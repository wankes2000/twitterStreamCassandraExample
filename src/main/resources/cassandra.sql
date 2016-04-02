CREATE KEYSPACE twitter WITH replication = {
  'class': 'SimpleStrategy',
  'replication_factor': '1'
};

USE twitter;


CREATE TABLE tweets (
  user_id bigint,
  date timestamp,
  name text,
  num_events int,
  text text,
  PRIMARY KEY ((user_id), date)
) WITH CLUSTERING ORDER BY (date DESC) AND
  bloom_filter_fp_chance=0.010000 AND
  caching='KEYS_ONLY' AND
  comment='' AND
  dclocal_read_repair_chance=0.100000 AND
  gc_grace_seconds=864000 AND
  index_interval=128 AND
  read_repair_chance=0.000000 AND
  replicate_on_write='true' AND
  populate_io_cache_on_flush='false' AND
  default_time_to_live=0 AND
  speculative_retry='99.0PERCENTILE' AND
  memtable_flush_period_in_ms=0 AND
  compaction={'class': 'SizeTieredCompactionStrategy'} AND
  compression={'sstable_compression': 'LZ4Compressor'};
