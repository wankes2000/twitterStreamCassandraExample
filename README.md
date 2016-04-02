# Twitter Stream 2 Cassandra Docker Example



#### Start First Cassandra Server
```
docker run --name first-cassandra -d cassandra:latest
```

#### Start Second Cassandra Server
```
docker run --name second-cassandra -d --link first-cassandra:cassandra cassandra:latest
```

#### Obtanin Cassandra Docker Container IP
```
docker inspect yourContainerName
```

#### Log into Cassandra Docker Container

```
docker exec -t -i yourContainerName /bin/bash
```

#### Running App

```
mvn spring-boot:run
```
