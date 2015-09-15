package org.avol.tweet.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Durga on 9/15/2015.
 */

@Entity
@Table(name = "TWEET")
@Setter @Getter
public class TweetEntity {

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "POSTED_ON", nullable = false)
    private String date;
}
