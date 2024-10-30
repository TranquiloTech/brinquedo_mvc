# Usando uma imagem base do Ubuntu para construção
FROM ubuntu:latest AS build

# Atualizando o repositório e instalando o JDK e Maven
RUN apt-get update && apt-get install openjdk-17-jdk maven -y

# Definindo o diretório de trabalho para a construção
WORKDIR /app

# Copiando o arquivo pom.xml e o código-fonte do projeto para o container
COPY pom.xml .
COPY src ./src

# Executando a construção do Maven, ignorando os testes
RUN mvn clean install -DskipTests

# Usando uma imagem base mais leve para executar o aplicativo
FROM openjdk:17-jdk-slim

# Expor a porta 8080
EXPOSE 8080

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o arquivo JAR gerado para a imagem final
COPY --from=build /app/target/brinquedos-0.0.1-SNAPSHOT.jar app.jar

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]