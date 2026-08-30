# IBM Bob 2.0 — hackathon operating rules

Source: *IBM TechXchange 2026 Pre-conference Dev Day Hackathon Guide* + Bob mode docs.

## Product constraint

The judged core is **Bob IDE** (Agent / Plan / Ask, subagents, document understanding, rules, skills). Do not pivot the repo into a watsonx-only demo. Local MCP is already in `.bob/mcp.json`. Do not add **remote** MCP that exfiltrates the tree.

Onboarding: skill `onboard-project` or mode **Onboarding Coach**. Tests: `run-tests` / `write-unit-tests`. Libraries: `upgrade-deps`. Security: `security-fix`.

## Cost (Bobcoins)

- Prefer `@path` over “read the project”.
- Plan mode: Plan subagents are **Explore-only**. Do not expect a Coder subagent there.
- Ask / Plan: Explore subagents only.
- After Plan, humans should start a **new chat** before Agent. If you are still in the planning conversation, do not start a large implementation.
- Never load the hackathon PDF, `scr/`, or `bob_sessions/*.png` into context.

## Evidence

Humans store PNG summaries in `bob_sessions/` (`team_taskNN_desc.png`). Do not delete the folder. Do not put secrets there.

Do **not** create `internal-monologue/` (Bob tutorial pattern). It pollutes git; session evidence is PNG in `bob_sessions/`.

## MCP

Project MCP: `.bob/mcp.json` → local `mcp/pexwalls-context-server.mjs` (read-only AGENTS / tests / catalog pins). No API keys. Enable “Use MCP Servers” in Bob settings. Do not add remote MCP that exfiltrates the tree.

## Account (humans, do not automate login)

Hackathon instance: `ibm-coding-challenge-uat` (us-east), not a personal Bob plan.

## Document understanding

- App spec = Kotlin + Gradle + this `AGENTS.md`
- Event spec = the PDF (Bobcoins, `bob_sessions/`, no PI)
- If they conflict, **app spec** governs code; **PDF** governs submission process
