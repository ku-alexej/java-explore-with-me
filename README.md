# Этап 3. Дополнительная функциональность

Пулл реквест: [ссылка](https://github.com/ku-alexej/java-explore-with-me/pull/5)

Реализована функциональность `Comments`

Эндпоинты для `Admin`:
- `Get /admin/comments?from={from}&size={size}` - получение списка комментариев
- `Delete /admin/comments/{commentId}` - удаление комментария по id

Эндпоинты для `Private`:
- `Post /users/{userId}/comments?eventId={eventId}` - создание пользователем комментария из данных DTO
- `Get /users/{userId}/comments?eventId={eventId}&from={from}&size={size}` - получение списка комментариев к событию или всем событиям пользователя
- `Patch /users/{userId}/comments/{commentId}` - обновление пользователем комментария из данных DTO
- `Delete /users/{userId}/comments/{commentId}` - удаление пользователем комментария по ID

Эндпоинты для `Public`
- `Get /comments/{commentId}` - получение комментария по ID
- `Get /comments?eventId={eventId}&from={from}&size={size}` - пполучение списка комментариев к событию
