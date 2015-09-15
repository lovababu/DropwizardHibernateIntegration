package org.avol.tweet.conf;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.avol.tweet.TweetConfiguration;
import org.avol.tweet.dao.TweetDao;
import org.avol.tweet.dao.impl.TweetDaoImpl;
import org.avol.tweet.entities.TweetEntity;
import org.avol.tweet.healthchecks.DBHealthCheck;
import org.avol.tweet.service.TweetBusinessService;
import org.avol.tweet.service.impl.TweetBusinessServiceImpl;
import org.hibernate.SessionFactory;

/**
 * Created by Durga on 9/14/2015.
 * <p/>
 * Google Guice Bindings class, It defines the DI graph.
 */
@Slf4j
public class TweetModule extends AbstractModule {

    //Hibernate Bundle, tells where to find Entity annotated classes and database configuration.
    private final HibernateBundle<TweetConfiguration> hibernateBundle = new HibernateBundle<TweetConfiguration>(TweetEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TweetConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    @Override
    protected void configure() {
        log.info("TweetModule.configure");
        //Linked Bindings.
        bind(TweetDao.class).to(TweetDaoImpl.class);
        bind(TweetBusinessService.class).to(TweetBusinessServiceImpl.class);
    }

    /**
     * Provides injectable SessionFactory to DAO classes.
     *
     * Basically, we can hand over HibernatBundle to bootstrap class to manage, but injection is not possible in this case.
     * So took this approach to provide more Convenient way to inject SessionFactory.
     *
     * @param configuration
     * @param environment
     * @return
     * @throws Exception
     */
    @Provides
    @Singleton
    public SessionFactory provieSessionFactory(TweetConfiguration configuration, Environment environment) throws Exception {
        log.info("TweetModule.sessionFactory");
        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
        if (sessionFactory == null) {
            hibernateBundle.run(configuration, environment);
            return hibernateBundle.getSessionFactory();
        }
        return sessionFactory;
    }


    @Provides
    @Singleton
    public DBHealthCheck dbHealthCheck(SessionFactory sessionFactory) {
        return new DBHealthCheck(sessionFactory);
    }
}
