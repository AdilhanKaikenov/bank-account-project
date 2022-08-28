**Microservices that are based on CQRS & Event Sourcing.
Powered by Spring Boot and Apache Kafka.**

Command API is responsible for handling the rights or commands, and the bank account query API responsible for handling the reads.

The events store will be responsible for persisting a new events to the event store via the events store repository. And once an event has been successfully persisted to the event store, it will publish a new event to Kafka through the event publisher interface.

On the query side, there will be an event consumer that subscribes to the account event topic and once a new instance of the account event is published to Kafka. It will consume it from Kafka and then pass it to the event handler. The event handler will then be responsible for handling the account event, whereby it will build up an account entity that it will persist to the read database.

Client can, for example, make a find all accounts query request over to the bank account query restful API.

![pic-01](https://raw.githubusercontent.com/AdilhanKaikenov/bank-account-project/main/architecture/architecture_pic.jpg)


- Java 11
- Maven
- Docker
- Kafka
- MongoDB
- MySQL
- Robo 3T 1.4.4 (mongo graphical user interface)

Run Kafka in Docker: `./docker-kafka/docker-compose.yml`

Run MongoDB in Docker:
``
docker run -it -d --name mongo-container -p 27017:27017 --network techbankNet --restart always -v mongodb_data_container:/data/db mongo:latest
``

Run MySQL in Docker:
``
docker run -it -d --name mysql-container -p 3306:3306 --network techbankNet -e MYSQL_ROOT_PASSWORD=techbankRootPsw --restart always -v mysql_data_container:/var/lib/mysql mysql:latest
``

``
docker run -it -d --name adminer -p 8085:8085 --network techbankNet -e ADMINER_DEFAULT_SERVER=mysql-container --restart always adminer:latest
``