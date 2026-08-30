#!/usr/bin/env python3
"""
android-maven-lookup — MCP server (stdio, JSON-RPC 2024-11-05)
Global MCP server for IBM Bob's Android - Deps Assist mode.

Tools:
  maven_get_latest_version  — fetch latest stable version for a Maven coordinate
  maven_get_latest_versions — fetch latest stable versions for multiple coordinates in one call
  maven_check_google_play_requirements — fetch live targetSdk/compileSdk requirements from
                                         developer.android.com

No API keys required. Calls:
  - Maven Central Search API  https://search.maven.org/solrsearch/select
  - Google Maven index         https://dl.google.com/dl/android/maven2/master-index.xml
                               https://dl.google.com/dl/android/maven2/<group-path>/group-index.xml
  - Google Play requirements   https://developer.android.com/google/play/requirements/target-sdk
"""

import json
import sys
import urllib.request
import urllib.error
import urllib.parse
import xml.etree.ElementTree as ET
import re
from typing import Any

# ---------------------------------------------------------------------------
# JSON-RPC helpers
# ---------------------------------------------------------------------------

def _send(obj: dict) -> None:
    sys.stdout.write(json.dumps(obj) + "\n")
    sys.stdout.flush()

def _ok(req_id: Any, result: dict) -> None:
    _send({"jsonrpc": "2.0", "id": req_id, "result": result})

def _err(req_id: Any, msg: str, code: int = -32000) -> None:
    _send({"jsonrpc": "2.0", "id": req_id, "error": {"code": code, "message": msg}})

def _text(req_id: Any, text: str) -> None:
    _ok(req_id, {"content": [{"type": "text", "text": text}]})

def _error_text(req_id: Any, text: str) -> None:
    _ok(req_id, {"content": [{"type": "text", "text": text}], "isError": True})

# ---------------------------------------------------------------------------
# HTTP helpers
# ---------------------------------------------------------------------------

TIMEOUT = 10  # seconds

def _get(url: str) -> str:
    req = urllib.request.Request(url, headers={"User-Agent": "android-maven-lookup/1.0"})
    with urllib.request.urlopen(req, timeout=TIMEOUT) as resp:
        return resp.read().decode("utf-8")

# ---------------------------------------------------------------------------
# Maven Central lookup
# ---------------------------------------------------------------------------

def _latest_from_maven_central(group: str, artifact: str) -> dict:
    """Query Maven Central Search API for the latest stable release."""
    coord = f"{group}:{artifact}"
    url = (
        "https://search.maven.org/solrsearch/select"
        f"?q=g:{urllib.parse.quote(group)}+AND+a:{urllib.parse.quote(artifact)}"
        "&rows=1&wt=json&core=gav"
    )
    try:
        raw = _get(url)
        data = json.loads(raw)
        docs = data.get("response", {}).get("docs", [])
        # Filter out pre-releases: no -alpha, -beta, -rc, -SNAPSHOT suffixes
        stable = [
            d["v"] for d in docs
            if not re.search(r"(?i)(-alpha|-beta|-rc|-snapshot|\.rc)", d.get("v", ""))
        ]
        if stable:
            return {"coordinate": coord, "latestStable": stable[0], "source": "Maven Central"}
        # fallback: take first doc regardless
        if docs:
            return {"coordinate": coord, "latestStable": docs[0]["v"],
                    "source": "Maven Central (may include pre-release)"}
        return {"coordinate": coord, "latestStable": "UNVERIFIED",
                "source": "Maven Central — no results"}
    except Exception as exc:
        return {"coordinate": coord, "latestStable": "UNVERIFIED",
                "source": f"Maven Central — FETCH FAILED: {exc}"}

# ---------------------------------------------------------------------------
# Google Maven lookup
# ---------------------------------------------------------------------------

def _latest_from_google_maven(group: str, artifact: str) -> dict:
    """Query Google Maven group-index XML for the latest stable release."""
    coord = f"{group}:{artifact}"
    group_path = group.replace(".", "/")
    url = f"https://dl.google.com/dl/android/maven2/{group_path}/group-index.xml"
    try:
        raw = _get(url)
        root = ET.fromstring(raw)
        # Each child element name is an artifact id; attribute 'versions' is comma-separated
        for elem in root:
            if elem.tag == artifact:
                versions_str = elem.attrib.get("versions", "")
                versions = [v.strip() for v in versions_str.split(",") if v.strip()]
                # Newest is last in the list; filter pre-releases
                stable = [
                    v for v in reversed(versions)
                    if not re.search(r"(?i)(-alpha|-beta|-rc|-snapshot)", v)
                ]
                if stable:
                    return {"coordinate": coord, "latestStable": stable[0],
                            "source": "Google Maven"}
                if versions:
                    return {"coordinate": coord, "latestStable": versions[-1],
                            "source": "Google Maven (may include pre-release)"}
        return {"coordinate": coord, "latestStable": "UNVERIFIED",
                "source": f"Google Maven — artifact '{artifact}' not found in group index"}
    except Exception as exc:
        return {"coordinate": coord, "latestStable": "UNVERIFIED",
                "source": f"Google Maven — FETCH FAILED: {exc}"}

# ---------------------------------------------------------------------------
# Router: pick registry by group prefix
# ---------------------------------------------------------------------------

_GOOGLE_PREFIXES = ("androidx.", "com.google.", "com.android.", "android.", "org.jetbrains.kotlin.")

def _lookup_one(coordinate: str) -> dict:
    """Return {coordinate, latestStable, source} for a single group:artifact coordinate."""
    parts = coordinate.strip().split(":")
    if len(parts) < 2:
        return {"coordinate": coordinate, "latestStable": "UNVERIFIED",
                "source": "invalid coordinate — expected group:artifact"}
    group, artifact = parts[0], parts[1]
    if any(group.startswith(p) for p in _GOOGLE_PREFIXES):
        return _latest_from_google_maven(group, artifact)
    return _latest_from_maven_central(group, artifact)

# ---------------------------------------------------------------------------
# Google Play requirements fetch
# ---------------------------------------------------------------------------

def _fetch_google_play_requirements() -> dict:
    """
    Fetch the current targetSdk / compileSdk requirements page from
    developer.android.com and return the raw text + extracted key facts.
    """
    url = "https://developer.android.com/google/play/requirements/target-sdk"
    try:
        html = _get(url)
        # Strip HTML tags for a readable plain-text summary
        text = re.sub(r"<[^>]+>", " ", html)
        text = re.sub(r"[ \t]{2,}", " ", text)
        text = re.sub(r"\n{3,}", "\n\n", text)
        # Extract a focused excerpt around key requirement phrases
        lines = text.splitlines()
        relevant = []
        keywords = ["target api", "targetsdk", "target sdk", "api level", "requirement",
                    "new apps", "existing apps", "august", "november", "deadline", "must target"]
        for i, line in enumerate(lines):
            if any(kw in line.lower() for kw in keywords):
                start = max(0, i - 1)
                end = min(len(lines), i + 3)
                relevant.extend(lines[start:end])
                relevant.append("---")
        excerpt = "\n".join(relevant[:120]).strip() or text[:3000]
        return {
            "url": url,
            "status": "fetched",
            "excerpt": excerpt,
            "note": (
                "Raw excerpt from developer.android.com. "
                "Read the full page for authoritative requirements and deadline dates."
            ),
        }
    except Exception as exc:
        return {
            "url": url,
            "status": f"FETCH FAILED: {exc}",
            "excerpt": "",
            "note": (
                "Could not reach developer.android.com. "
                "Check network connectivity and retry, or visit the URL manually."
            ),
        }

# ---------------------------------------------------------------------------
# Tool registry
# ---------------------------------------------------------------------------

TOOLS = [
    {
        "name": "maven_get_latest_version",
        "description": (
            "Fetch the latest stable version for a single Maven coordinate "
            "(group:artifact). Automatically routes to Google Maven for androidx.*, "
            "com.google.*, com.android.*, org.jetbrains.kotlin.* groups, "
            "and to Maven Central for everything else. "
            "Returns {coordinate, latestStable, source}."
        ),
        "inputSchema": {
            "type": "object",
            "properties": {
                "coordinate": {
                    "type": "string",
                    "description": "Maven coordinate in group:artifact format, e.g. 'androidx.room:room-runtime'",
                }
            },
            "required": ["coordinate"],
        },
    },
    {
        "name": "maven_get_latest_versions",
        "description": (
            "Fetch the latest stable versions for multiple Maven coordinates in one call. "
            "Pass a list of group:artifact strings. Returns a JSON array of "
            "{coordinate, latestStable, source} objects."
        ),
        "inputSchema": {
            "type": "object",
            "properties": {
                "coordinates": {
                    "type": "array",
                    "items": {"type": "string"},
                    "description": "List of Maven coordinates in group:artifact format",
                }
            },
            "required": ["coordinates"],
        },
    },
    {
        "name": "maven_check_google_play_requirements",
        "description": (
            "Fetch the live Google Play target API level requirements from "
            "developer.android.com/google/play/requirements/target-sdk. "
            "Returns a raw excerpt of the page. Use this instead of relying on "
            "training-data snapshots for targetSdk / compileSdk compliance checks."
        ),
        "inputSchema": {
            "type": "object",
            "properties": {},
        },
    },
]

# ---------------------------------------------------------------------------
# Request handler
# ---------------------------------------------------------------------------

def _handle(msg: dict) -> None:
    method = msg.get("method", "")
    req_id = msg.get("id")

    if method == "initialize":
        _ok(req_id, {
            "protocolVersion": "2024-11-05",
            "capabilities": {"tools": {}},
            "serverInfo": {"name": "android-maven-lookup", "version": "1.0.0"},
        })
        return

    if method in ("notifications/initialized", "notifications/cancelled"):
        return

    if method == "tools/list":
        _ok(req_id, {"tools": TOOLS})
        return

    if method == "tools/call":
        name = msg.get("params", {}).get("name", "")
        args = msg.get("params", {}).get("arguments", {})

        if name == "maven_get_latest_version":
            coord = args.get("coordinate", "").strip()
            if not coord:
                _error_text(req_id, "Error: 'coordinate' is required.")
                return
            result = _lookup_one(coord)
            _text(req_id, json.dumps(result, indent=2))

        elif name == "maven_get_latest_versions":
            coords = args.get("coordinates", [])
            if not coords:
                _error_text(req_id, "Error: 'coordinates' list is required.")
                return
            results = [_lookup_one(c) for c in coords]
            _text(req_id, json.dumps(results, indent=2))

        elif name == "maven_check_google_play_requirements":
            result = _fetch_google_play_requirements()
            _text(req_id, json.dumps(result, indent=2))

        else:
            _error_text(req_id, f"Unknown tool: {name}")
        return

    if req_id is not None:
        _err(req_id, f"Method not found: {method}", code=-32601)

# ---------------------------------------------------------------------------
# Main loop
# ---------------------------------------------------------------------------

def main() -> None:
    print("android-maven-lookup MCP server running on stdio", file=sys.stderr)
    for line in sys.stdin:
        line = line.strip()
        if not line:
            continue
        try:
            msg = json.loads(line)
            _handle(msg)
        except json.JSONDecodeError as exc:
            print(f"JSON parse error: {exc}", file=sys.stderr)
        except Exception as exc:
            print(f"Unhandled error: {exc}", file=sys.stderr)

if __name__ == "__main__":
    main()
