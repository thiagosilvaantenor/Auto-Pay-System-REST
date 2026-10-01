# Auto Pay System REST (PoC)

Microsserviço de processamento e autorização de pagamentos corporativos (B2B), desenvolvido como Prova de Conceito (PoC) com foco em design de software e cultura DevOps.

O sistema recebe solicitações de pagamento, executa validações de regras de negócio (limites e CNPJ) e roteia a solicitação para aprovação automática ou fila de revisão manual, mantendo um rastro de auditoria rigoroso das alterações.

## 🏛️ Arquitetura e Padrões (Engineering Focus)

Este projeto foi construído para demonstrar maturidade arquitetural e aderência a padrões corporativos:

*   **Clean Architecture (Ports and Adapters):** Regra de negócio isolada no centro da aplicação (`domain`), comunicando-se com o mundo exterior (`infrastructure` e `presentation`) exclusivamente através de interfaces (Portas).
*   **Domain-Driven Design (DDD):** Uso de Entidades Ricas que gerenciam seu próprio estado e regras (ex: `Pagamento.processarAutorizacao()`), evitando o anti-pattern de modelos anêmicos.
*   **Event Sourcing (Conceitual):** Mutações de estado geram eventos imutáveis (`PagamentoCriadoEvent`, `PagamentoEditadoEvent`). Na PoC, utilizamos o `ApplicationEventPublisher` do Spring para simular o comportamento de um *Message Broker*. A arquitetura permite substituição direta por **Kafka, AWS SQS ou SNS** apenas trocando o adaptador de infraestrutura.
*   **SOLID:** Princípios aplicados rigorosamente, com destaque para Inversão de Dependência (D) e Responsabilidade Única (S).

## 🛠️ Tecnologias Utilizadas

*   **Java 17+** & **Spring Boot 3**
*   **Spring Data JPA** & **H2 Database** (In-memory persistency)
*   **Hibernate Validator** (Validação matemática de CNPJ)
*   **Docker** & **Apache Maven** (Containerização e Build)
*   **Arquitetura:** Clean Architecture, DDD, Event-Driven

## 🚀 Como Executar o Projeto

### Pré-requisitos
*   Java 17 ou superior
*   Docker (Opcional, para execução via container)

### Opção 1: Rodando via Docker (Recomendado)
A aplicação está conteinerizada. Para construir e rodar a imagem, execute na raiz do projeto:

```bash
# 1. Empacotar a aplicação via Maven
./mvnw clean package -DskipTests

# 2. Construir a imagem Docker
docker build -t auto-pay-system-rest:latest .

# 3. Rodar o container
docker run -p 8080:8080 --name pagamentos-app auto-pay-system-rest:latest
```

### Opção 2: Rodando Local (Maven)
```bash
./mvnw spring-boot:run
````

O banco de dados H2 pode ser acessado em: http://localhost:8080/h2-console
(JDBC URL: jdbc:h2:mem:pagamentosdb, User: sa, Password: em branco)


## 🧪 Testando a API (Endpoints)
### 1. Solicitar Pagamento (Aprovação Automática)

Valores menores ou iguais a R$ 10.000,00 são aprovados automaticamente.

```bash

curl -X POST http://localhost:8080/api/pagamentos \
-H "Content-Type: application/json" \
-d '{
"cnpjPagador": "00000000000191",
"valor": 5000.00,
"destino": "Fornecedor TI"
}'
````

### 2. Solicitar Pagamento (Fila de Aprovação)

Valores maiores que R$ 10.000,00 vão para o status AGUARDANDO_APROVACAO_MANUAL.
````Bash

curl -X POST http://localhost:8080/api/pagamentos \
-H "Content-Type: application/json" \
-d '{
"cnpjPagador": "00000000000191",
"valor": 15000.00,
"destino": "Consultoria Externa"
}'
````

### 3. Editar Pagamento (Auditoria)

Simula a edição de um pagamento pendente por um usuário autenticado. O sistema gera um evento de auditoria registrando o valor anterior e o novo valor. Substitua {ID_DO_PAGAMENTO} pelo UUID retornado no endpoint anterior.
```Bash

curl -X PUT http://localhost:8080/api/pagamentos/{ID_DO_PAGAMENTO} \
-H "Content-Type: application/json" \
-H "X-Usuario-Logado: analista.financeiro" \
-d '{
"novoValor": 9500.00
}'
````

Se o novo valor for inferior ao teto, o sistema automaticamente autoriza o pagamento.
🛣️ Roadmap e Próximos Passos (Evolução Cloud)

Como a base do sistema segue a Clean Architecture, as seguintes evoluções para produção envolveriam apenas a criação de novos adaptadores de infraestrutura, sem tocar na regra de negócio:

    AWS Integration: Substituir o repositório H2 por uma implementação conectada ao Amazon DynamoDB ou RDS.

    Mensageria Distribuída: Implementar um adaptador para publicar os eventos de domínio (Auditoria) no AWS SNS/SQS ou Apache Kafka.

    Observabilidade: Adicionar o Spring Boot Actuator exportando métricas via Micrometer para análise no Grafana e tracing no Jaeger.

    Orquestração: Criação dos manifestos do Kubernetes para deploy no AWS EKS.
