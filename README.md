<h1 align="center">
  HubSpot Integration Api
</h1>

<div align="center">

<img src="https://img.shields.io/badge/maven-3.9.9-blue" />
<img src="https://img.shields.io/badge/Java-21-blue" />
<img src="https://img.shields.io/badge/Spring_Boot-3.4.2-darkgreen" />

</div>

## Prerequisites

- Ngrok (https://ngrok.com/docs/getting-started/)
- Docker
- Docker compose 2

## Run

#### 1. Adicionar ```CLIENT_ID``` e ```CLIENT_SECRET``` fornecidos pelo hubspot ao ```application-local.yml``` 

#### 2. Subir a aplicação:

```
docker compose up -d
```

#### 3. Usar ngrok para registar webhook para o localhost

```
ngrok http http://localhost:8080
```

![img.png](img.png)

#### 4. Copiar fowarding url e registrar o webhook no hubspot

Exemplo: ```a.b.c.ngrok-free.app/webhook```

![img_1.png](img_1.png)

#### 5. Criar assinatura para o objeto ```Contato``` e evento ```Criado``` no hubspot

![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)

#### 6. Gerar URL de autorização

http://localhost:8080/oauth/authorize

#### 7. Acessar swagger para salvar contatos

Iniciar fluxo oauth:

http://localhost:8080/oauth/authorize

OU

Via swagger
http://localhost:8080/swagger-ui/index.html

## Using
- Clean Arquitecture
- Docker/Docker compose
- Tests (UnitTests)