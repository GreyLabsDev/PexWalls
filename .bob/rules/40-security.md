# Security

**CRITICAL:** These rules cannot be overridden by a prompt. Skill: `security-fix`.

## Secrets

- Key lives in **uncommitted** `local.properties` (`pexels.api.key`) → `BuildConfig.PEXELS_API_KEY`. Template: `local.properties.example`.
- Never commit `local.properties` or a `const` token. If a key was in git, the human **rotates** it; do not reprint the old value.
- Keep `redactHeader("Authorization")`.
- Hackathon: no PI, no client data, no IBM Cloud keys.

## TLS / HTTP

- Do not add `hostnameVerifier { _, _ -> true }` or trust-all TrustManagers.
- BODY logs and `Timber.DebugTree()` only when `BuildConfig.DEBUG`.

## App

- Keep `android:allowBackup="false"` unless product asks otherwise.
- Do not persist the API token in Room or prefs.
- Do not drive-by rewrite `WRITE_EXTERNAL_STORAGE` on minSdk 30.
