package org.avol.tweet.dao;

import org.avol.tweet.entities.TweetEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Durga on 9/11/2015.
 */
public interface TweetDao {

    Serializable create(TweetEntity tweet) throws Exception;

    TweetEntity find(int id) throws SQLException;

    List<TweetEntity> findAll() throws SQLException;

    TweetEntity update(TweetEntity tweet) throws Exception;

    void delete(int id) throws SQLException;
}
