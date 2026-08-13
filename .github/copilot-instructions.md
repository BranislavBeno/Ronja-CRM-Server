# Ronja CRM Server — Copilot Instructions

Spring Boot 4 REST API (Java 25) for a CRM: customers, representatives, and scheduled
metal-price (copper/aluminum/lead) lookups from the London Metal Exchange. Persists to MySQL
via Flyway-managed schema.

## Build, test, lint

- Build/compile: `./gradlew build` (Windows: `gradlew.bat build`)
- Run all tests + coverage: `./gradlew jacocoTestReport` (this is what CI runs)
- Run a single test class: `./gradlew test --tests "com.ronja.crm.ronjaserver.service.CustomerServiceTest"`
- Run a single test method: `./gradlew test --tests "com.ronja.crm.ronjaserver.service.CustomerServiceTest.methodName"`
- Repository/integration tests (`*RepositoryTest`, `*IT`) use Testcontainers and require a running
  Docker daemon — they self-disable (`disabledWithoutDocker = true`) if Docker isn't available.
- Code style/migration recipes: `./gradlew rewriteRun` applies the OpenRewrite recipes declared in
  `rewrite.yml` and `build.gradle` (`activeRecipe(...)` — Java migration, Spring Boot upgrade,
  import ordering, no-static-import in `validator` package).
- SonarQube analysis (needs `SONAR_TOKEN`): `./gradlew sonar`.

## Architecture

- **Layering**: `controller` → `service` → `repository` → `entity`, with `dto` +
  `dto/*Mapper` classes converting between entities and API DTOs (no MapStruct — mappers are
  plain hand-written classes). Controllers never expose entities directly.
- **Service interfaces**: `EntityService<T>` defines generic CRUD (`findAll`, `findById`,
  `existsById`, `save`, `deleteById`); `ExtendedEntityService<T>` extends it with
  `findByCustomerId` and `findScheduledForNextNDays` (used by `RepresentativeService`).
  Services are implemented as **records** wrapping a repository (e.g. `CustomerService`), not
  classes with fields.
- **Bean wiring**: Service/mapper/webclient beans are NOT annotated with `@Service`/`@Component`;
  they're constructed and wired explicitly in `config/ServiceConfig` via `@Bean` methods. When
  adding a new service or mapper, register it in `ServiceConfig` rather than adding stereotype
  annotations.
- **Controllers** depend on the `EntityService<T>` interface type (not the concrete service), and
  throw `EntityNotFoundException` (handled centrally by `exception/GlobalExceptionHandler`, a
  `@ControllerAdvice`) for missing entities on update/delete. Error responses are hand-serialized
  JSON via Jackson's `tools.jackson` API (Spring Boot 4's new Jackson 3 packages), not the classic
  `com.fasterxml.jackson`.
- **Validation**: custom Bean Validation constraints live in `validator/` as annotation +
  `ConstraintValidator` pairs (`@Category`, `@Focus`, `@Status`, `@ContactType`, `@Currency`),
  used on both entity fields and DTOs to keep allowed enum-like string values in sync.
- **Representative contacts**: `phoneNumbers`/`emails` are `List<Contact>` persisted as JSON
  strings via `ListAttributeConverter` (a JPA `AttributeConverter`), not a separate join table.
- **Metal exchange integration**: `client/api/MetalExchangeWebClient` is a reactive `WebClient`
  wrapper (Reactor Netty, 2s timeouts, retry) calling metals-api.com; `MetalDataService` fetches
  and persists rates. The exchange fetch job is scheduled via the `client.metal.cron.expression`
  property (Mon–Fri 23:00), configured in `application.properties`, not hardcoded in code.
- **Schema migrations**: managed by Flyway under `src/main/resources/db/migration`, named
  `V<major>_<minor>_<patch>__description.sql`. Never edit a committed migration — add a new
  versioned file for schema changes.
- **Testing conventions**: repository tests extend `BaseRepositoryTest` (Testcontainers MySQL via
  `@ServiceConnection`); web/service tests supporting the metal exchange client use
  `ContainersConfig` which also spins up a `MockServerContainer` for stubbing the external API.
  `CustomerControllerRestAssuredTest` shows the REST-Assured + MockMvc style used alongside plain
  MockMvc tests (`CustomerControllerTest`).
- User-facing validation/error messages are in Slovak (e.g. "Zákazník", "Neznáma kategória.") —
  keep new user-facing messages consistent with this convention.
