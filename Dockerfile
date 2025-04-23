# Etapa 1: Construção do artefato
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de configuração do Maven (caso exista) para otimizar cache de dependências
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copia o código-fonte do projeto para dentro do container
COPY src ./src

# Compila o projeto e gera o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para execução
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho na nova imagem
WORKDIR /app

# Copia o jar gerado da etapa de build para a nova imagem
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
