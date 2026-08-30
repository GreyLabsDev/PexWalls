# Prompt — Unit Test Coach

Use mode **Unit Test Coach** if available, otherwise **Ask** (report) then **Agent** (add).

Activate skill `unit-test-coach` (`/unit-test-coach` if needed).

Compare JVM unit tests with `.bob/rules/35-testing.md`: what is covered, which gaps can be closed on the JVM, and what cannot (Fragment/Glide/Wallpaper). Do not touch androidTest. Do not add Mockito.

If I did not name classes — report only plus three next tickets. If I named classes or said "add" — write the tests and update the inventory table.
