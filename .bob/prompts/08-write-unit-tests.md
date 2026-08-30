# Prompt — Agent: add unit tests

Use **Agent** mode. Activate skill `write-unit-tests`.

Add JVM tests following the coverage map in `.bob/rules/35-testing.md`. Do not add Mockito. Network tests: MockWebServer only. Use case tests: use `FakeRepository`. DAO tests: Robolectric is already set up. Reuse `PhotoFixtures`. Update the inventory table in `35-testing.md` if a new test class is added.
