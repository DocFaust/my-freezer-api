# MyFreezer API

Eine REST-API zur Verwaltung von Tiefkühlprodukten und Kategorien, entwickelt mit Spring Boot.

## 🚀 Features

- **Produktverwaltung**: CRUD-Operationen für Tiefkühlprodukte
- **Kategorienverwaltung**: Organisation von Produkten in Kategorien
- **Optimistic Locking**: Verhindert gleichzeitige Änderungen durch Versionskontrolle
- **Audit-Felder**: Automatische Nachverfolgung von Erstellungs- und Änderungszeitpunkten
- **Datenbank-Migrationen**: Flyway für versionierte Schema-Verwaltung
- **Health Monitoring**: Spring Boot Actuator für Systemüberwachung

## 📋 Voraussetzungen

- Java 21 oder höher
- Maven 3.6+
- PostgreSQL 12+ (oder Docker für lokale Entwicklung)
- Optional: Docker & Docker Compose

## 🛠️ Technologie-Stack

- **Framework**: Spring Boot 4.0.1
- **Datenbank**: PostgreSQL (Produktion), H2 (Tests)
- **ORM**: Spring Data JPA / Hibernate
- **Migration**: Flyway
- **Build Tool**: Maven
- **Code Quality**: Checkstyle, JaCoCo, SonarCloud

## 🏃 Schnellstart

### Lokale Entwicklung mit Docker Compose

1. **Datenbank starten**:
   ```bash
   docker-compose up -d
   ```

2. **Anwendung starten**:
   ```bash
   ./mvnw spring-boot:run
   ```

Die Anwendung läuft auf `http://localhost:8080`

### Manuelle Einrichtung

1. **PostgreSQL Datenbank erstellen**:
   ```sql
   CREATE DATABASE mydatabase;
   CREATE USER myuser WITH PASSWORD 'secret';
   GRANT ALL PRIVILEGES ON DATABASE mydatabase TO myuser;
   ```

2. **Konfiguration anpassen** (`src/main/resources/application.yml`):
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/mydatabase
       username: myuser
       password: secret
   ```

3. **Anwendung starten**:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📚 API-Endpunkte

### Produkte

- `GET /api/v1/products/` - Alle Produkte abrufen
- `GET /api/v1/products/{id}` - Einzelnes Produkt abrufen
- `POST /api/v1/products` - Neues Produkt erstellen
- `PUT /api/v1/products/{id}` - Produkt aktualisieren
- `DELETE /api/v1/products/{id}` - Produkt löschen

### Kategorien

- `GET /api/v1/categories/` - Alle Kategorien abrufen
- `GET /api/v1/categories/{id}` - Einzelne Kategorie abrufen
- `POST /api/v1/categories` - Neue Kategorie erstellen
- `PUT /api/v1/categories/{id}` - Kategorie aktualisieren
- `DELETE /api/v1/categories/{id}` - Kategorie löschen

### Beispiel-Request (Produkt erstellen)

```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Tiefkühlpizza",
    "description": "Margherita",
    "categoryId": 1
  }'
```

## 🗄️ Datenbank-Migrationen

Das Projekt verwendet [Flyway](https://flywaydb.org/) für Datenbank-Migrationen.

### Migrationen ausführen

Migrationen werden automatisch beim Start der Anwendung ausgeführt. Für manuelle Migrationen:

```bash
./mvnw flyway:migrate
```

### Neue Migration erstellen

1. Neue SQL-Datei im Format `V{version}__{description}.sql` erstellen:
   ```
   src/main/resources/db/migration/V2__Add_audit_fields.sql
   ```

2. SQL-Skript schreiben

3. Migration wird beim nächsten Start automatisch ausgeführt

## 🧪 Tests

### Alle Tests ausführen

```bash
./mvnw test
```

### Mit Coverage-Report

```bash
./mvnw test jacoco:report
```

Der Coverage-Report befindet sich in `target/site/jacoco/index.html`

## 📊 Monitoring

Spring Boot Actuator ist auf Port `8081` konfiguriert:

- Health Check: `http://localhost:8081/actuator/health`
- Metrics: `http://localhost:8081/actuator/metrics`
- Prometheus: `http://localhost:8081/actuator/prometheus`

## 🏗️ Projektstruktur

```
src/
├── main/
│   ├── java/de/docfaust/myfreezerapi/
│   │   ├── config/          # Konfigurationsklassen
│   │   ├── controller/       # REST Controller
│   │   ├── data/
│   │   │   ├── entity/       # JPA Entities
│   │   │   └── repository/   # Spring Data Repositories
│   │   ├── dto/              # Data Transfer Objects
│   │   └── service/          # Business Logic
│   └── resources/
│       ├── application.yml   # Anwendungskonfiguration
│       └── db/migration/      # Flyway Migrationen
└── test/                      # Test-Klassen
```

## 🔧 Konfiguration

### Umgebungsvariablen

Die Anwendung unterstützt Konfiguration über Umgebungsvariablen:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydatabase
export SPRING_DATASOURCE_USERNAME=myuser
export SPRING_DATASOURCE_PASSWORD=secret
```

### Profile

Spring Boot Profile können für verschiedene Umgebungen verwendet werden:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📝 Code-Qualität

### Checkstyle

```bash
./mvnw checkstyle:check
```

### SonarCloud

Das Projekt ist für SonarCloud konfiguriert. Reports werden automatisch generiert.

## 🚨 Bekannte Einschränkungen / Technical Debt

- ⚠️ **Keine Authentifizierung**: API ist derzeit öffentlich zugänglich
- ⚠️ **Keine Pagination**: List-Endpunkte laden alle Datensätze
- ⚠️ **Hardcoded Credentials**: Datenbank-Credentials sollten externalisiert werden
- ⚠️ **Keine API-Dokumentation**: OpenAPI/Swagger noch nicht implementiert

Siehe [Technical Debt Analyse](#) für Details.

## 🤝 Beitragen

1. Fork das Projekt
2. Erstelle einen Feature-Branch (`git checkout -b feature/AmazingFeature`)
3. Committe deine Änderungen (`git commit -m 'Add some AmazingFeature'`)
4. Push zum Branch (`git push origin feature/AmazingFeature`)
5. Öffne einen Pull Request

## 📄 Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert.

## 👤 Autor

**docfaust**

## 🙏 Danksagungen

- Spring Boot Team
- Flyway Team
- Alle Open-Source-Contributors