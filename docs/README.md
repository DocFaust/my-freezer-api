# My Freezer API – Dokumentation

REST-API zur Verwaltung von Gefrierschrank-Kategorien und -Produkten.

| Dokument | Beschreibung |
|----------|--------------|
| [API-Referenz](api.md) | Endpoints, Datenmodelle, Statuscodes, Beispiele |

## Schnellstart

```bash
# PostgreSQL (Docker)
docker compose up -d

# Anwendung
./mvnw spring-boot:run
```

- **API-Basis-URL:** `http://localhost:8080`
- **Actuator:** `http://localhost:8081/actuator`
- **API-Version:** `v1` (Pfadpräfix `/api/v1`)

## Manuelle Tests

HTTP-Requests liegen unter [`http/`](../http/) (REST Client / VS Code HTTP Client).

## Technologie

- Java 25, Spring Boot 4.0.1
- PostgreSQL, JPA/Hibernate, Flyway
- Spring Boot Actuator (separater Management-Port)
