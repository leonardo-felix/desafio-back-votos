# desafio-back-votos
Projeto em de API rest para pautas e votos.

## Tecnologias utilizadas
* Spring boot
* PostgresSQL
* Kafka
* H2 database(Testes)
* Junit(Tests)

### Como rodar o projeto localmente?

Para executar a aplicação e suas dependências, basta ter no computador instalado o docker-compose e executar o comando abaixo:
````
docker--composer up --build
````

Após isso, os serviços já serão executados e estão disponíveis em http://localhost:8080/swagger-ui.html

### Para execução dos testes, é possivel testa-los via:
````
mvn test
````

#### Itens desenvolvidos
* Tarefa Principal
    * Tarefa Bônus 1 - Integração com sistemas externos
    * Tarefa Bônus 2 - Mensageria e filas
    * Tarefa Bônus 3 - Performance
