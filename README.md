# Sistema de Adopción de Mascotas

Aplicación web desarrollada con Spring Boot para la gestión de mascotas disponibles para adopción. El sistema incluye frontend con Thymeleaf, backend en Java, persistencia con MySQL, autenticación y autorización con Spring Security, y pruebas unitarias con JUnit.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8
- Docker
- JUnit
- JaCoCo

## Arquitectura

El proyecto sigue una arquitectura en 3 capas:

- Capa de presentación: controladores y vistas Thymeleaf.
- Capa de negocio: servicios encargados de la lógica de la aplicación.
- Capa de persistencia: repositorios JPA y entidades del modelo.

La estructura principal del proyecto se organiza en paquetes como `controller`, `service`, `repository`, `model`, `security` y `config`.

## Base de datos

La aplicación utiliza una base de datos MySQL llamada:

```text
adopcion_mascotas_db
```

El esquema SQL de creación se encuentra en:

```text
database/schema.sql
```

Aunque el proyecto utiliza entidades JPA/Hibernate para mapear las tablas de la base de datos, se incluye este archivo SQL como respaldo formal del esquema solicitado en la pauta de entrega.

## Variables de entorno

Antes de ejecutar la aplicación, se deben configurar las variables de entorno necesarias para la conexión a la base de datos y la firma de tokens JWT.

Ejemplo en PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3307/adopcion_mascotas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="CAMBIAR_PASSWORD_LOCAL"
$env:JWT_SECRET="CAMBIAR_POR_UN_SECRETO_LOCAL_LARGO"
$env:JWT_EXPIRATION="3600000"
```

## Base de datos con Docker

Para iniciar el contenedor MySQL utilizado por el proyecto:

```powershell
docker start mysql-adopcion-db
```

Para verificar que la base de datos está disponible:

```powershell
docker exec -it mysql-adopcion-db mysql -uroot -p -e "SHOW DATABASES;"
```

Para cargar manualmente el esquema SQL desde PowerShell:

```powershell
Get-Content -Raw database/schema.sql | docker exec -i mysql-adopcion-db mysql -uroot -p
```

## Ejecución de la aplicación

Con MySQL iniciado y las variables de entorno cargadas, ejecutar:

```powershell
.\mvnw spring-boot:run
```

La aplicación queda disponible en:

```text
http://localhost:8080
```

## Pruebas unitarias

Para ejecutar las pruebas unitarias:

```powershell
.\mvnw clean test
```

Para generar el reporte de cobertura con JaCoCo:

```powershell
.\mvnw clean verify
```

El reporte de cobertura se genera en:

```text
target/site/jacoco/index.html
```

## Evidencias de seguridad consideradas

Para la entrega final del proyecto se consideran las siguientes evidencias:

- Reporte OWASP ZAP para validar vulnerabilidades OWASP Top 10.
- Reporte de análisis estático de código.
- Reporte de escáner de vulnerabilidades.
- Reporte SCA para revisión de dependencias.
- Reporte de pruebas unitarias y cobertura mínima del 60%.
