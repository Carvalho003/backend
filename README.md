# Encanto Personalizados - Backend

API do sistema Encanto Personalizados, desenvolvida em Java com Spring Boot. O backend centraliza autenticacao, cadastro de clientes e usuarios, catalogo de produtos personalizados, pedidos, movimentacoes financeiras, dashboards, uploads de imagens e integracao com WhatsApp.

## Tecnologias

- Java 21
- Spring Boot 3.5.5
- Maven Wrapper
- Spring Web, Data JPA, Security, Validation e Actuator
- MySQL
- Redis para cache
- RabbitMQ para mensageria
- JWT para autenticacao
- Springdoc OpenAPI/Swagger
- Docker e Docker Compose

## Estrutura

```text
backend/
├── EncantoPersonalizados/       # Aplicacao Spring Boot
│   ├── src/main/java/...        # Codigo fonte
│   ├── src/main/resources/      # Configuracoes e data.sql
│   ├── src/test/java/...        # Testes automatizados
│   ├── API WHATSAPP/            # Servico auxiliar de WhatsApp
│   ├── docker-compose.yml       # MySQL, RabbitMQ, Redis e WhatsApp API
│   ├── Dockerfile               # Build da API Java
│   └── pom.xml                  # Dependencias Maven
├── uploads/                     # Arquivos enviados pela aplicacao
└── .env.example                 # Exemplo de variaveis para WhatsApp API
```

## Requisitos

- JDK 21
- Docker Desktop
- Maven nao e obrigatorio, pois o projeto inclui `mvnw.cmd` e `mvnw`

## Configuracao

As principais configuracoes estao em `EncantoPersonalizados/src/main/resources/application.properties`.

Variaveis de ambiente aceitas pela aplicacao:

| Variavel | Padrao local | Descricao |
| --- | --- | --- |
| `DB_URL` | `jdbc:mysql://127.0.0.1:3307/encanto_personalizados?...` | URL JDBC do MySQL |
| `DB_USERNAME` | `root` | Usuario do banco |
| `DB_PASSWORD` | `encanto` | Senha do banco |
| `RABBITMQ_HOST` | `localhost` | Host do RabbitMQ |
| `RABBITMQ_PORT` | `5672` | Porta do RabbitMQ |
| `RABBITMQ_USERNAME` | `cynapse` | Usuario do RabbitMQ |
| `RABBITMQ_PASSWORD` | `encanto` | Senha do RabbitMQ |
| `WHATSAPP_API_BASE_URL` | `http://localhost:3001` | URL da API auxiliar de WhatsApp |
| `WHATSAPP_API_USER` | vazio | Usuario de basic auth da API WhatsApp |
| `WHATSAPP_API_PASSWORD` | vazio | Senha de basic auth da API WhatsApp |
| `WHATSAPP_SCHEDULER_ENABLED` | `false` | Liga/desliga envio automatico de mensagens |

Observacao: mantenha tokens e segredos reais fora do Git. Use variaveis de ambiente para ambientes compartilhados ou producao.

## Como rodar localmente

Entre na pasta da aplicacao:

```powershell
cd backend\EncantoPersonalizados
```

Suba os servicos de apoio:

```powershell
docker compose up -d mysql rabbitmq redis redisinsight whatsapp_api
```

Inicie a API:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficara disponivel em:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- RabbitMQ Management: `http://localhost:15672`
- RedisInsight: `http://localhost:5540`
- WhatsApp API: `http://localhost:3001`

## Comandos uteis

Rodar testes:

```powershell
.\mvnw.cmd test
```

Gerar pacote sem testes:

```powershell
.\mvnw.cmd package -DskipTests
```

Gerar pacote com relatorio JaCoCo:

```powershell
.\mvnw.cmd verify
```

Parar os servicos Docker:

```powershell
docker compose down
```

## Principais dominios da API

- `/login`: autenticacao e troca de senha
- `/usuarios`: usuarios/funcionarios
- `/clientes`: clientes e enderecos
- `/produtos`: produtos e fotos
- `/temas`: temas de produto
- `/categoria-temas`: categorias de temas
- `/itens`: itens de produto
- `/pedidos`: pedidos e produtos de pedido
- `/status-pedidos`: etapas/status do kanban
- `/movimentacoes`: entradas e saidas financeiras
- `/categoria-movimentacoes`: categorias financeiras
- `/contrapartes`: fornecedores, clientes ou contrapartes financeiras
- `/dashfinanceiros`: dashboard financeiro
- `/dashgestaopedidos`: dashboard de gestao de pedidos
- `/fretes`: calculo de frete
- `/whatsapp`: envio e consulta de mensagens de pedidos pendentes
- `/messages`: publicacao de mensagens no broker

Para detalhes de payloads e telas consumidoras, consulte `EncantoPersonalizados/API_ENDPOINTS_COMPLETE.md`.

## Observacoes de desenvolvimento

- O banco local padrao usa MySQL em `localhost:3307`.
- O Hibernate esta configurado com `ddl-auto=update`.
- O arquivo `data.sql` e executado na inicializacao para popular dados base.
- Uploads sao gravados no diretorio configurado por `upload.directory`, com padrao `uploads`.
- Algumas funcionalidades dependem de RabbitMQ, Redis ou WhatsApp API; suba esses servicos quando for testar fluxos relacionados.
