# RedisDemoApplication

Pequeña aplicación de ejemplo que muestra cómo usar Spring Boot con Redis (spring-data-redis).

Descripción
-----------
Demo educativa que expone unos endpoints HTTP para guardar, recuperar y borrar valores en Redis, y un método de ejemplo con caché. Ideal para probar integración entre una aplicación Spring Boot y un servidor Redis.

Requisitos
---------
- JDK 21
- Maven
- (Opcional) Docker y Docker Compose si quieres ejecutar Redis y la app en contenedores

Configuración clave
-------------------
La aplicación usa la propiedad `spring.data.redis.host` configurada por la variable de entorno `REDIS_HOST`. Por defecto busca Redis en `localhost`. En Docker Compose se inyecta `REDIS_HOST=redis` para apuntar al servicio Redis del compose.

Ejecución local (con Redis en localhost o con Docker)
----------------------------------------------------
1. Si no tienes Redis instalado, puedes arrancarlo con Docker:

```bash
docker run --name redis -p 6379:6379 -d redis:7
```

2. Ejecutar la aplicación con Maven:

```bash
mvn spring-boot:run
```

La aplicación quedará disponible en http://localhost:8080

Ejecución con Docker Compose
----------------------------
1. Arrancar los servicios (esto construye la imagen de la app y levanta Redis):

```bash
docker-compose up --build
```

2. La app quedará disponible en http://localhost:8080

Endpoints
---------
- POST /redis/{key}      — Guardar un valor (cuerpo: texto plano)
- GET  /redis/{key}      — Obtener el valor almacenado
- DELETE /redis/{key}    — Borrar la clave
- GET  /redis/byId/{id}  — Ejemplo de método cacheado (la primera llamada mostrará "Buscando item...")

Ejemplos (curl)
---------------
Guardar un valor:

```bash
curl -X POST http://localhost:8080/redis/foo -d "hola"
```

Obtener un valor:

```bash
curl http://localhost:8080/redis/foo
```

Borrar un valor:

```bash
curl -X DELETE http://localhost:8080/redis/foo
```

Ejemplo cacheado (primera llamada hace la búsqueda):

```bash
curl http://localhost:8080/redis/byId/123
```

Compilación y tests
-------------------
- Ejecutar tests unitarios:

```bash
mvn test
```

- Crear el JAR ejecutable:

```bash
mvn package
```

- Ejecutar el JAR:

```bash
java -jar target/*.jar
```
