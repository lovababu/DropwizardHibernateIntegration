package org.avol.tweet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.avol.tweet.dao.TweetDao;
import org.avol.tweet.entities.TweetEntity;
import org.avol.tweet.service.TweetBusinessService;

import javax.inject.Inject;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Durga on 9/11/2015.
 */
@Slf4j
public class TweetBusinessServiceImpl implements TweetBusinessService {

    @Inject
    private TweetDao tweetDao;

    @Override
    public Serializable create(TweetEntity tweet) throws Exception {
        log.info("TweetBusinsessServiceImpl.create");
        return tweetDao.create(tweet);
    }

    @Override
    public TweetEntity find(int id) throws SQLException {
        return tweetDao.find(id);
    }

    @Override
    public List<TweetEntity> findAll() throws SQLException {
        return tweetDao.findAll();
    }

    @Override
    public TweetEntity update(TweetEntity tweet) throws Exception {
        return tweetDao.update(tweet);
    }

    @Override
    public void delete(int id) throws SQLException {
        tweetDao.delete(id);
    }
}
