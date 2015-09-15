package org.avol.tweet;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Durga on 9/14/2015.
 *
 * This class instantiated by DropWizard with YAML configuration.
 */
public class TweetConfiguration extends Configuration{

    @NotEmpty
    @Setter @Getter
    private String contextPath;

    @NotNull
    @Valid
    @Setter @Getter
    private DataSourceFactory database;
}
