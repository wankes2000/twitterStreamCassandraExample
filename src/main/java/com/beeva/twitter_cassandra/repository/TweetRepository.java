package com.beeva.twitter_cassandra.repository;

import org.springframework.data.repository.CrudRepository;

import com.beeva.twitter_cassandra.model.Tweet;
import com.beeva.twitter_cassandra.model.TweetPrimaryKey;

public interface TweetRepository extends CrudRepository<Tweet, TweetPrimaryKey> {

}
