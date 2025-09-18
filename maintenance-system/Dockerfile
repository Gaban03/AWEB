# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml e baixa dependências primeiro (cache melhor)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código
COPY . .

# Build sem rodar testes
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
