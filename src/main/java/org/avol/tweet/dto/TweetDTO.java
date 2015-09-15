package org.avol.tweet.dto;

import org.avol.tweet.api.Tweet;
import org.avol.tweet.entities.TweetEntity;

/**
 * Created by Durga on 9/15/2015.
 */
public final class TweetDTO {

    private TweetDTO() {
        //Util class should not be instantiated.
    }

    public static Tweet tweet(TweetEntity tweetEntity) {
        Tweet tweet = new Tweet();
        //TODO: should use beanUtils libraries.
        tweet.setId(tweetEntity.getId());
        tweet.setMessage(tweetEntity.getMessage());
        tweet.setDate(tweetEntity.getDate());
        return tweet;
    }

    public static TweetEntity tweetEntity(Tweet tweet) {
        TweetEntity tweetEntity = new TweetEntity();
        //TODO: should use beanUtils libraries.
        tweetEntity.setId(tweet.getId());
        tweetEntity.setMessage(tweet.getMessage());
        tweetEntity.setDate(tweet.getDate());
        return tweetEntity;
    }
}
