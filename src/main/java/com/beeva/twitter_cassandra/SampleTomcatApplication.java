/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beeva.twitter_cassandra;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.beeva.twitter_cassandra.model.Tweet;
import com.beeva.twitter_cassandra.model.TweetPrimaryKey;
import com.beeva.twitter_cassandra.repository.TweetRepository;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import static com.beeva.twitter_cassandra.constants.GenericConstants.TWITTER4J_ACCESS_TOKEN_ENV;
import static com.beeva.twitter_cassandra.constants.GenericConstants.TWITTER4J_ACCESS_TOKEN_SECRET_ENV;
import static com.beeva.twitter_cassandra.constants.GenericConstants.TWITTER4J_DEBUG_ENV;
import static com.beeva.twitter_cassandra.constants.GenericConstants.TWITTER4J_OAUTH_CONSUMER_KEY_ENV;
import static com.beeva.twitter_cassandra.constants.GenericConstants.TWITTER4J_OAUTH_CONSUMER_SECRET_ENV;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleTomcatApplication implements CommandLineRunner{
	
	@Autowired
	private TweetRepository tweetRepository;

	private static Log logger = LogFactory.getLog(SampleTomcatApplication.class);
	



	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleTomcatApplication.class, args);
		
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		StatusListener listener = new StatusListener(){
			Long tweetsProcessed = 0L;
			Long tweetsCreated = 0L;
			
	        public void onStatus(Status status) {
	        	tweetsProcessed++;
	            TweetPrimaryKey tweetPrimaryKey = new TweetPrimaryKey(status.getUser().getId(),status.getCreatedAt());
	            Tweet tweet = new Tweet(status.getUser().getName(), Arrays.asList(status.getText()),1);
	            if (tweetRepository.exists(tweetPrimaryKey)){
	            	tweet = tweetRepository.findOne(tweetPrimaryKey);
	            	tweet.setNumEvents(tweet.getNumEvents()+1);
	            	List<String> texts = new ArrayList<String>(tweet.getTexts());
	            	texts.add(status.getText());
	            	tweet.setTexts(texts);
	            } else {
	            	tweet.setPk(tweetPrimaryKey);
	            	tweetsCreated++;
	            }
	            tweetRepository.save(tweet);
	            System.out.println("Created - " +tweetsCreated +" Processed "+tweetsProcessed + " - "+status.getUser().getName() + " : " + status.getText());
		           

	        }
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
	    };
	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(Boolean.getBoolean(System.getenv(TWITTER4J_DEBUG_ENV)))
	      .setOAuthConsumerKey(System.getenv(TWITTER4J_OAUTH_CONSUMER_KEY_ENV))
	      .setOAuthConsumerSecret(System.getenv(TWITTER4J_OAUTH_CONSUMER_SECRET_ENV))
	      .setOAuthAccessToken(System.getenv(TWITTER4J_ACCESS_TOKEN_ENV))
	      .setOAuthAccessTokenSecret(System.getenv(TWITTER4J_ACCESS_TOKEN_SECRET_ENV));
	    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	    

	    
	    twitterStream.addListener(listener);
	    // Filter
	    /*
	    FilterQuery filtre = new FilterQuery();
	    String[] keywordsArray = { "Podemos","PSOE","Ciudadanos","PP","IU","PNV","CIU" };
	    String[] languagesArray = {"es"};
	    filtre.track(keywordsArray).language(languagesArray);
	    twitterStream.filter(filtre);*/
	    twitterStream.sample();

		
	}

	

}
