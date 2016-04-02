package com.beeva.twitter_cassandra.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * CREATE TABLE tweets ( user_id bigint, name varchar, text varchar, date
 * timestamp,num_events int, PRIMARY KEY(user_id,date)) with CLUSTERING ORDER BY (date DESC);
 */

@Table("tweets")
public class Tweet {

	@PrimaryKey
	private TweetPrimaryKey pk;

	@Column(value = "name")
	private String name;

	@Column(value = "text")
	private String text;
	
	@Column(value = "num_events")
	private int numEvents;
	
	

	public Tweet( String name, String text,int numEvents) {
		super();
		this.name = name;
		this.text = text;
		this.numEvents = numEvents;
	}

	public TweetPrimaryKey getPk() {
		return pk;
	}

	public void setPk(TweetPrimaryKey pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumEvents() {
		return numEvents;
	}

	public void setNumEvents(int numEvents) {
		this.numEvents = numEvents;
	}

}
