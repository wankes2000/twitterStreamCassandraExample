# Twitter Stream 2 Cassandra Docker Example



#### Start Cassandra Server
```
docker run -d spotify/cassandra
```

#### Obtanin Cassandra Docker Container IP
```
docker inspect yourContainerName
```

#### Log into Cassandra Docker Container

```
docker exec -t -i yourContainerName /bin/bash
```