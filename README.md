# Twitter Stream 2 Cassandra Docker Example



#### Start First Cassandra Server
```
docker run --name first-cassandra -d cassandra:2
```

#### Start Second Cassandra Server
```
docker run --name second-cassandra -d --link first-cassandra:cassandra cassandra:2
```

#### Obtanin Cassandra Docker Container IP
```
docker inspect first-cassandra
```

#### Log into Cassandra Docker Container

```
docker exec -t -i first-cassandra /bin/bash
```

#### Running App

```
mvn spring-boot:run
```
