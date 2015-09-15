package org.avol.tweet.dao.impl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.avol.tweet.dao.TweetDao;
import org.avol.tweet.entities.TweetEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Durga on 9/11/2015.
 *
 *
 */
@Slf4j
public class TweetDaoImpl implements TweetDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public Serializable create(TweetEntity tweetEntity) throws Exception {
        log.info("TweetDaoImpl.create: " + sessionFactory);
        Preconditions.checkNotNull(sessionFactory);
        return sessionFactory.getCurrentSession().save(tweetEntity);
    }

    @Override
    public TweetEntity find(int id) throws SQLException {
        return (TweetEntity) sessionFactory.getCurrentSession().get(TweetEntity.class, id);
    }

    @Override
    public List<TweetEntity> findAll() throws SQLException {
        Query query = sessionFactory.getCurrentSession().createQuery("From TweetEntity");
        return query.list();
    }

    @Override
    public TweetEntity update(TweetEntity tweetEntity) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(tweetEntity);
        return tweetEntity;
    }

    @Override
    public void delete(int id) throws SQLException {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from TweetEntity where id = :id");
        query.setInteger("id", id);
        int i = query.executeUpdate();
        log.info("No of records deleted: " + i);
    }
}
