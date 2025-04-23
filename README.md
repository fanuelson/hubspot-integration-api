# hubspot-integration-api

![Maven Badge](https://img.shields.io/badge/maven-3.9.9-blue)
![Java Badge](https://img.shields.io/badge/Java-21-blue)
![Spring Boot Badge](https://img.shields.io/badge/Spring_Boot-3.4.2-darkgreen)

## Prerequisites

- Ngrok (https://ngrok.com/docs/getting-started/)
- Docker
- Docker compose 2

## Run

1. Adicionar ```CLIENT_ID``` e ```CLIENT_SECRET``` fornecidos pelo hubspot ao application.yml 
2. Executar a aplicação:
```
$ docker compose up -d
```

Access:
http://localhost:8080/swagger-ui/index.html

3. Usar ngrok para registar webhook para o localhost
```ngrok http http://localhost:8080```
![img.png](img.png)

4. Copiar fowarding url e adicionar a assinatura do hubspot
Exemplo: ```a.b.c.ngrok-free.app/webhook```
![img_1.png](img_1.png)

## Using
- Clean Arquitecture
- Docker/Docker compose
- Tests (UnitTests)