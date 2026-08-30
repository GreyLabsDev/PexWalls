# Prompt — Agent: add unit tests

Use **Agent** mode. Activate skill `write-unit-tests`.

Добавь JVM-тесты по карте в `.bob/rules/35-testing.md`. Mockito не подключай. Сеть — только MockWebServer. Use case — `FakeRepository`. DAO — уже есть Robolectric. Переиспользуй `PhotoFixtures`. Обнови таблицу в `35-testing.md`, если появился новый класс тестов.
