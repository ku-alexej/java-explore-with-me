# Explore With Me

**Explore With Me** is a Java/Spring Boot backend for a service that lets people share and discover local events (concerts, exhibitions, meetups, etc.) and find companions to attend them with. Users can propose events, browse and filter events created by others, and send requests to participate; event organizers can review, confirm, or reject participation requests. Administrators manage users, event categories, curated compilations of events, and moderate events and comments.

The project is built as a multi-module Maven application composed of two independent Spring Boot microservices that communicate over REST:

- **`main-service`** — the core business logic: users, events, categories, compilations, participation requests, and comments.
- **`stats-service`** — a lightweight statistics service that records every hit (view) on public endpoints and returns view/visit statistics on demand. `main-service` calls `stats-service` internally to save hits and to fetch view counters for events.

Both services run as separate applications, each with its own PostgreSQL database, and are containerized with Docker Compose.

## Tech stack

- Java, Spring Boot, Spring MVC, Spring Data JPA
- PostgreSQL (separate database per service)
- Maven (multi-module project)
- Docker / Docker Compose
- Lombok
- Checkstyle for code style enforcement
- OpenAPI specifications (`ewm-main-service-spec.json`, `ewm-stats-service-spec.json`) describing the contracts of both services
- A Postman collection (`postman/`) for manual API testing

## Project structure

```
java-explore-with-me/
├── main-service/                  # Core service: events, users, categories, requests, comments
├── stats-service/                 # Statistics service: hits & views
├── postman/                       # Postman collections for API testing
├── docker-compose.yml             # Container orchestration for both services + databases
├── ewm-main-service-spec.json     # OpenAPI spec for the main service
├── ewm-stats-service-spec.json    # OpenAPI spec for the stats service
├── checkstyle.xml / suppressions.xml
└── pom.xml                        # Parent Maven module
```

## Access levels

The main service exposes three groups of endpoints, matching the roles that can access them:

- **Public** — no authentication required; open to any visitor. Only returns published, publicly visible data.
- **Private** — actions performed by an authenticated regular user on their own resources (their events, requests, comments).
- **Admin** — administrative operations for managing the whole platform (users, categories, compilations, event moderation, comment moderation).

## API — Main Service

### Users (Admin)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/admin/users` | Get information about users (supports filtering by IDs, pagination) |
| POST | `/admin/users` | Add a new user |
| DELETE | `/admin/users/{userId}` | Delete a user |

### Categories

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/admin/categories` | Admin | Add a new category |
| PATCH | `/admin/categories/{catId}` | Admin | Update a category |
| DELETE | `/admin/categories/{catId}` | Admin | Delete a category |
| GET | `/categories` | Public | Get the list of categories (pagination) |
| GET | `/categories/{catId}` | Public | Get category info by id |

### Compilations (curated collections of events)

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/admin/compilations` | Admin | Create a new compilation (may contain no events) |
| PATCH | `/admin/compilations/{compId}` | Admin | Update a compilation |
| DELETE | `/admin/compilations/{compId}` | Admin | Delete a compilation |
| GET | `/compilations` | Public | Get compilations (filterable by "pinned", pagination) |
| GET | `/compilations/{compId}` | Public | Get a compilation by id |

### Events

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/admin/events` | Admin | Search events (by users, states, categories, date range) |
| PATCH | `/admin/events/{eventId}` | Admin | Edit an event's data and status (publish/reject) |
| GET | `/events` | Public | Search published events with filters: `text`, `categories`, `paid`, `rangeStart`, `rangeEnd`, `onlyAvailable`, `sort` (by date or by views), `from`/`size` |
| GET | `/events/{id}` | Public | Get full details of a published event by id |
| POST | `/users/{userId}/events` | Private | Add a new event |
| GET | `/users/{userId}/events` | Private | Get events created by the current user |
| GET | `/users/{userId}/events/{eventId}` | Private | Get full details of the current user's event |
| PATCH | `/users/{userId}/events/{eventId}` | Private | Update the current user's event |

### Participation requests

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/users/{userId}/requests` | Private | Submit a request to participate in someone else's event |
| GET | `/users/{userId}/requests` | Private | Get the current user's own participation requests |
| PATCH | `/users/{userId}/requests/{requestId}/cancel` | Private | Cancel the user's own participation request |
| GET | `/users/{userId}/events/{eventId}/requests` | Private | Get requests to participate in the current user's event |
| PATCH | `/users/{userId}/events/{eventId}/requests` | Private | Confirm or reject requests for the current user's event |

### Comments

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/users/{userId}/comments?eventId={eventId}` | Private | Create a comment on an event |
| GET | `/users/{userId}/comments?eventId={eventId}&from={from}&size={size}` | Private | Get the current user's comments (for one event or all their events) |
| PATCH | `/users/{userId}/comments/{commentId}` | Private | Update the current user's comment |
| DELETE | `/users/{userId}/comments/{commentId}` | Private | Delete the current user's comment |
| GET | `/comments/{commentId}` | Public | Get a comment by id |
| GET | `/comments?eventId={eventId}&from={from}&size={size}` | Public | Get the list of comments for an event |
| GET | `/admin/comments?from={from}&size={size}` | Admin | Get the list of all comments |
| DELETE | `/admin/comments/{commentId}` | Admin | Delete any comment by id |

## API — Statistics Service

The stats service is a small, independent service used to track and report endpoint usage (primarily views of events).

| Method | Endpoint | Description |
|---|---|---|
| POST | `/hit` | Save information that a request was made to an endpoint (app name, URI, IP, timestamp) |
| GET | `/stats` | Get visit statistics for a date/time range, optionally filtered by a list of URIs, with an option to count only unique visitors (`unique`) |

Timestamps passed to `/stats` (`start`/`end`) must be URL-encoded (e.g. via `java.net.URLEncoder.encode`).

## Running the project

The project is designed to run with Docker Compose, which starts both services along with their own PostgreSQL databases:

```bash
mvn clean package
docker-compose up
```

Once running, `main-service` and `stats-service` communicate over HTTP: every request to a public event-related endpoint is recorded as a "hit" via the stats service, and the main service queries the stats service to display view counts for events.

## API testing

The `postman/` directory contains Postman collections that can be imported to manually exercise both services' endpoints.

## OpenAPI specifications

Full request/response schemas, validation rules, and status codes for both services are documented in:

- [`ewm-main-service-spec.json`](./ewm-main-service-spec.json)
- [`ewm-stats-service-spec.json`](./ewm-stats-service-spec.json)

## Origin

This repository was generated from the [yandex-praktikum/java-explore-with-me](https://github.com/yandex-praktikum/java-explore-with-me) template as a learning/training project.
