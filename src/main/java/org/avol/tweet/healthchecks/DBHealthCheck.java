package org.avol.tweet.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import org.hibernate.SessionFactory;

/**
 * Created by Durga on 9/11/2015.
 */
public class DBHealthCheck extends HealthCheck {

    private SessionFactory sessionFactory;

    public DBHealthCheck(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Result check() throws Exception {
        boolean flag =  sessionFactory.openSession().isConnected();
        return flag ? Result.healthy() : Result.unhealthy("Connection not valid.");
    }
}
