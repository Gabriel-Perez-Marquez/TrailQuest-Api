# =========================
# Stage 1: build (Maven + JDK 21)
# =========================
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos primero lo mínimo para cachear dependencias
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# Asegurar permisos + evitar problemas de formato CRLF (Windows) en mvnw
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Descargar dependencias (mejora el tiempo de rebuild)
RUN ./mvnw -DskipTests dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src/ src/
RUN ./mvnw -DskipTests package

# =========================
# Stage 2: runtime (JRE 21)
# =========================
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Usuario no-root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar el .jar compilado desde la fase anterior
COPY --from=build /app/target/*.jar /app/app.jar

# Copiar script de arranque
COPY ./docker/entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

USER spring:spring

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080

ENTRYPOINT ["/app/entrypoint.sh"]