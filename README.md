# Desafio Sistema Bancario para processo seletivo

## South System / Gustavo Torres

- [x] A Aplicação foi construída fazendo uso da arquitetura em Microserviços
- [x] A comunicação entre um serviço e outro é realizada de forma assíncrona
- [x] Usamos filas através do serviço de mensageria RabbitMQ
- [x] Há alguns testes unitários como amostra: com.gustavotorres.cadastropessoa.services.PessoaServiceTests
- [x] Foram utilizados design patterns como IoC, DI, Builder
- [x] Os tratamentos de erro e de exceção se concentram no diretório de exceções

Para um projeto completo ainda ficam sugeridos alguns módulos, que não foram incluidos no contexto deste teste

 * Integração de Autenticação e Autorização podendo usar SpringSecurity / OAUTH2
 * Módulo de logs

### Instalação e funcionamento
####     Pre-requisitos da instalação
        * JDK com Java 11 Instalado
        * Docker instalado

#### Passo a passo da instalação
        1. clonar os Repositórios
                - https://github.com/GustavoTrr/desafio-java-banco-pessoa-service
                - https://github.com/GustavoTrr/desafio-java-banco-conta-service
        2. Para cada repositório, Instalar as dependências 
        ```
        mvn clean install
        ``` 
        *pode se usar o "./mvnw" caso não possua Maven instalado na máquina
        e iniciar a aplicação
        ```
        mvn spring-boot:run
        ``` 
        3. Criar banco de dados
         * Pode criar com db = "desafiosouth_pessoaservice", usuário = "southsysuser",  e pass = ""
         ou pode criar um à sua escolha e depois configurar os dados de acesso no arquivo application.yml
         
        4. Instalar o serviço de mensageria:

        Separa uma pasta/diretório para o serviço RabbitMQ
cria o arquivo de configurações "docker-compose.yml", com as instruções abaixo:

version: '3'
services:
  rabbitmq:
    image: rabbitmq:3.8.3-management
	ports:
	 - 5672:5672
	 - 15672:15672
	volumes:
	 - $PWD/storage/rabbitmq1:/var/lib/rabbitmq
	enviroment:
	 - RABBITMQ_ERLANG_COOKIE=secret
	 - RABBITMQ_DEFAULUT_USER=admin
	 - RABBITMQ_DEFAULUT_PASS=admin
volumes:
  rabbitmq:

Executa os comandos
```
docker-compose up -d
```
Acessa o aplicativo http://localhost:15672/
cria a fila com o mesmo nome prfix da routing key gtorresbank.conta.queue
depois vai na exchanges -> clica na exchange específica -> clica em "Bindings"
Insere o nome da fila e o routingkey definido nas configurações do serviço de Conta



#### Testes
 - Para testar recomendamos utilizar o postman com as seguintes Requisições:
  - Cadastro
   - POST http://localhost:8081/api/pessoas
    - Passando como parametro um JSON com nome da Pessoa e CPF ou CNPJ válidos
    ``` 
        {
                "nome": "Novo Cliente 22",
                "numeroDocumento": "52223015085"
        }
        ``` 

 - Lista Pessoas
  - GET http://localhost:8081/api/pessoas
   - Pode se passar parâmetros opcionais de paginação como sort, size e page

 - Lista Contas com cartões e limites
  - GET http://localhost:8082/api/contas
    - Pode se passar parâmetros opcionais de paginação como sort, size e page

Para mais informações da API, utilizamos o Swagger, que pode ser encontrado nos endereços abaixos respectivos a acada aplicação:

 * http://localhost:8081/api/swagger-ui.html
 * http://localhost:8082/api/swagger-ui.html




## Descrição dos requisitos: 

### Desenvolva um sistema que seguirá o seguinte fluxo:

### 1. Cadastrar uma pessoa com os seguintes dados:
        Nome
        Tipo: Pessoa Física(PF) ou Juridica(PJ)
        Numero de documento (Tamanho 11 caso PF e tamanho 14 caso PJ)
        Score da pessoa (Valor randômico de 0 a 9)

### 2. Ao cadastrar uma pessoa, automaticamente, abrirá um conta corrente vinculada a essa pessoa, com os seguinte dados:
        Numero (6 digitos gerados únicos e gerados automaticamente)
        Agência (Parametrizavél via configuração)
        Tipo: Corrente (C) ou Empresarial (E) -> Selecinado automaticamente conforme a pessoa recebida
        Vinculo com a pessoa a qual a conta se refere

### 3. Após o cadastro da conta, devem ser criados o limite de cheque especial e um cartão de crédito, vinculados a conta criada, caso atendam os seguintes critérios:
        Se score for 0 ou 1, o limite de cheque especial estará desabilitado e não irá gerar cartão de crédito.
        Se score for entre 2 e 5 o limite de cheque especial será de R$ 1000,00 e o limite do cartão de crédito criado será de R$ 200,00
        Se score for entre 6 e 8 o limite de cheque especial será de R$ 2000,00 e o limite do cartão de crédito criado será de R$ 2000,00
        Se score for 9 o limite de cheque especial será de R$ 5000,00 e o limite do cartão de crédito criado será de R$ 15000,00
        *As faixas de limite poder ser parametrizaveis
    
### 4. Disponibilizar um endpoint em REST que liste as pessoas e outro que liste as contas e seus respectivos limites / cartões.

## Pontos de atenção:

