# Stage 1: build e instala o validator-core com as coordenadas que o fintech espera
FROM maven:3.9-eclipse-temurin-21-alpine AS build-core
WORKDIR /build/core
COPY validador-core/core/pom.xml pom.xml
COPY validador-core/core/src src
RUN mvn package -DskipTests -q && \
    mvn install:install-file \
      -Dfile=target/core-0.0.1-SNAPSHOT.jar \
      -DgroupId=com.validador \
      -DartifactId=validator-core \
      -Dversion=1.0.0 \
      -Dpackaging=jar \
      -q

# Stage 2: builda o fintech usando o .m2 do stage anterior
FROM maven:3.9-eclipse-temurin-21-alpine AS build-app
WORKDIR /build/app
COPY --from=build-core /root/.m2 /root/.m2
COPY fintech-legacy-credit/pom.xml pom.xml
RUN mvn dependency:go-offline -q 2>/dev/null || true
COPY fintech-legacy-credit/src src
RUN mvn package -DskipTests -q

# Stage 3: imagem de runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build-app /build/app/target/fintech-legacy-credit-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
