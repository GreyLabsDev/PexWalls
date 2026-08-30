---
name: security-fix
description: >
  Moves Pexels auth off git, keeps TLS defaults, debug-only HTTP BODY and Timber.
  Use when the user says security-fix, убери ключ, hostnameVerifier, hardcoded
  token, or Pexels API key in source.
user-invocable: true
---

# Pexels / TLS / logging security

Follow `.bob/rules/40-security.md`. **Never paste the old token into any file.**

## Current contract (already on disk)

- Key: `local.properties` → `pexels.api.key` → `BuildConfig.PEXELS_API_KEY` (`app/build.gradle.kts`).
- Example file: `local.properties.example` (no real key).
- `NetworkModule` must **not** contain a `const` token or `hostnameVerifier { _, _ -> true }`.
- BODY logs and `Timber.DebugTree()` only when `BuildConfig.DEBUG`.

## If the user asks to apply or re-apply

<Steps>
<Step>
Confirm `buildFeatures.buildConfig = true` and `buildConfigField("String", "PEXELS_API_KEY", ...)`.
</Step>
<Step>
`createNetworkInterceptor` reads `BuildConfig.PEXELS_API_KEY`. Keep `redactHeader("Authorization")`.
</Step>
<Step>
Do not add a custom `hostnameVerifier`. Do not add trust-all `TrustManager`.
</Step>
<Step>
Logging interceptor: BODY iff DEBUG, else NONE. `PexWallsApp` plants `Timber.DebugTree()` only in DEBUG.
</Step>
<Step>
Ensure `.gitignore` contains `local.properties`. Do not commit `local.properties`.
</Step>
</Steps>

If a token is still hardcoded, delete it from git history instructions: tell the human to rotate the Pexels key. Do not `git add` a key.
