#!/usr/bin/env node
/**
 * Local MCP (no secrets): read-only project map for IBM Bob.
 * Protocol: MCP JSON-RPC over stdio (2024-11-05).
 */
const fs = require("node:fs");
const path = require("node:path");
const readline = require("node:readline");

const ROOT = process.cwd();

function send(id, result) {
  const msg = { jsonrpc: "2.0", id, result };
  process.stdout.write(JSON.stringify(msg) + "\n");
}

function sendError(id, message) {
  process.stdout.write(
    JSON.stringify({ jsonrpc: "2.0", id, error: { code: -32000, message } }) + "\n"
  );
}

function readSafe(rel) {
  const abs = path.join(ROOT, rel);
  const resolved = path.resolve(abs);
  if (!resolved.startsWith(path.resolve(ROOT))) {
    throw new Error("path escape");
  }
  return fs.readFileSync(resolved, "utf8");
}

function listTests() {
  const dir = path.join(ROOT, "app/src/test/java");
  const out = [];
  function walk(d) {
    if (!fs.existsSync(d)) return;
    for (const name of fs.readdirSync(d)) {
      const p = path.join(d, name);
      if (fs.statSync(p).isDirectory()) walk(p);
      else if (name.endsWith("Test.kt")) out.push(path.relative(ROOT, p).replaceAll("\\", "/"));
    }
  }
  walk(dir);
  return out.sort();
}

const TOOLS = [
  {
    name: "get_agents_map",
    description:
      "Returns the first section of AGENTS.md so Bob does not re-scan the tree. No secrets.",
    inputSchema: { type: "object", properties: {} },
  },
  {
    name: "list_unit_tests",
    description: "Lists JVM *Test.kt files under app/src/test/java.",
    inputSchema: { type: "object", properties: {} },
  },
  {
    name: "list_catalog_pins",
    description: "Returns gradle/libs.versions.toml [versions] block only.",
    inputSchema: { type: "object", properties: {} },
  },
];

function handle(msg) {
  if (msg.method === "initialize") {
    send(msg.id, {
      protocolVersion: "2024-11-05",
      capabilities: { tools: {} },
      serverInfo: { name: "pexwalls-context", version: "1.0.0" },
    });
    return;
  }
  if (msg.method === "notifications/initialized" || msg.method === "notifications/cancelled") {
    return;
  }
  if (msg.method === "tools/list") {
    send(msg.id, { tools: TOOLS });
    return;
  }
  if (msg.method === "tools/call") {
    const name = msg.params?.name;
    try {
      let text = "";
      if (name === "get_agents_map") {
        text = readSafe("AGENTS.md").split("## Commands")[0].trim();
      } else if (name === "list_unit_tests") {
        text = listTests().join("\n");
      } else if (name === "list_catalog_pins") {
        const toml = readSafe("gradle/libs.versions.toml");
        text = toml.split("[libraries]")[0].trim();
      } else {
        sendError(msg.id, "unknown tool");
        return;
      }
      send(msg.id, {
        content: [{ type: "text", text }],
      });
    } catch (e) {
      sendError(msg.id, String(e.message || e));
    }
  }
}

const rl = readline.createInterface({ input: process.stdin });
rl.on("line", (line) => {
  if (!line.trim()) return;
  try {
    handle(JSON.parse(line));
  } catch (e) {
    process.stderr.write(String(e) + "\n");
  }
});
