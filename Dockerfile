# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

# Baixa dependências antes de copiar tudo (cache mais eficiente)
RUN mvn dependency:go-offline -B

# Agora builda o projeto (sem testes)
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final minimalista
FROM eclipse-temurin:17-jdk-alpine AS runtime

# Cria um usuário não-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copia apenas o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Permissões seguras
RUN chown -R appuser:appgroup /app

# Usa o usuário não-root
USER appuser

# Expõe apenas o necessário
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
