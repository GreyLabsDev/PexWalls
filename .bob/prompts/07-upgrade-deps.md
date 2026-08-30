# Prompt — Ask then Agent: libraries outdated or gone

Use **Ask** first (report only). Activate skill `upgrade-deps`.

Проверь библиотеки в `gradle/libs.versions.toml`: устаревшие, снятые с Maven, неподдерживаемые. Таблица: current / proposed / status / risk / apply. Ничего не бампай, пока я не назову артефакты.

If applying after approval, **new chat**, **Agent**:

Примени только эти пины: <list>. Не меняй AGP/wrapper, не меняй Glide на Coil и Koin на Hilt. Потом `:app:ktlint` и `:app:testDebugUnitTest`.
