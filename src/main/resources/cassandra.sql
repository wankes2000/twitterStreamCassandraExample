CREATE KEYSPACE twitter WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 2 };

USE twitter;

CREATE TABLE tweets (
  user_id bigint,
  date timestamp,
  name text,
  num_events int,
  texts list<text>,
  PRIMARY KEY ((user_id), date)
) WITH CLUSTERING ORDER BY (date DESC) ;

CREATE INDEX events  ON tweets ( num_events );
