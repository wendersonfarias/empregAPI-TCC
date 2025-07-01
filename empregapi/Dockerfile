# Estágio 1: Build da Aplicação com Maven

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean install -DskipTests

# ---

# Estágio 2: Imagem Final de Execução
# Use a mesma versão "slim" do Java para uma imagem menor
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Variáveis de ambiente padrão para a aplicação (perfil 'dev').
# Elas correspondem aos placeholders no application.properties
ENV SPRING_PROFILES_ACTIVE="dev"
ENV DB_URL="jdbc:postgresql://postgres-dev:5432/empregapi" 
ENV DB_USERNAME="wenderson"
ENV DB_PASSWORD="123"
ENV API_SECRET_KEY="supersecretkeydev"
ENV JPA_DDL_AUTO="update"

# Copia o JAR compilado do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação usa
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]
