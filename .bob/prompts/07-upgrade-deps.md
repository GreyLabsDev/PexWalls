# Prompt — Ask then Agent: libraries outdated or gone

Use **Ask** first (report only). Activate skill `upgrade-deps`.

Audit the libraries in `gradle/libs.versions.toml`: outdated, removed from Maven, unsupported. Produce a table: current / proposed / status / risk / apply. Do not bump anything until I name the artifacts.

If applying after approval, **new chat**, **Agent**:

Apply only these pins: <list>. Do not swap AGP/wrapper, do not replace Glide with Coil or Koin with Hilt. Then run `:app:ktlint` and `:app:testDebugUnitTest`.
