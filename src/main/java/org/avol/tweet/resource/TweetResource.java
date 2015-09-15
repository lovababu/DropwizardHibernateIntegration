package org.avol.tweet.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.avol.tweet.api.Tweet;
import org.avol.tweet.api.TweetResponse;
import org.avol.tweet.dto.TweetDTO;
import org.avol.tweet.entities.TweetEntity;
import org.avol.tweet.service.TweetBusinessService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Durga on 9/14/2015.
 *
 * Jersey Rest Resource class.
 */
@Path(value = "/tweet")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class TweetResource {

    private final TweetBusinessService tweetBusinessService;

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    public TweetResource(TweetBusinessService businessService) {
        this.tweetBusinessService = businessService;
    }

    @Timed
    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Tweet tweet) {
        log.info("Transaxtion Id: " + httpServletRequest.getAttribute("TranxId"));
        try {
            Serializable id  = tweetBusinessService.create(TweetDTO.tweetEntity(tweet));
            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setMessage("Tweet posted successfully: refID: " + id);
            tweetResponse.setStatusCode(Response.Status.CREATED.getStatusCode());
            tweet.setId((Integer)id);
            tweetResponse.setTweets(ImmutableList.of(tweet));
            return Response.status(Response.Status.CREATED).entity(tweetResponse)
                    .build();
        } catch (Exception e) {
            log.error("Error:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Timed
    @UnitOfWork(readOnly = true)
    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") int id) {
        log.info("Transaxtion Id: " + httpServletRequest.getAttribute("TranxId"));
        try {
            TweetEntity tweetEntity = tweetBusinessService.find(id);
            TweetResponse tweetResponse = new TweetResponse();
            if (tweetEntity != null) {
                tweetResponse.setTweets(ImmutableList.of(TweetDTO.tweet(tweetEntity)));
                tweetResponse.setStatusCode(Response.Status.OK.getStatusCode());
                return Response.status(Response.Status.OK).entity(tweetResponse).build();
            } else {
                tweetResponse.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
                return Response.status(Response.Status.OK).entity(tweetResponse).build();
            }
        } catch (SQLException e) {
            log.error("Error:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Timed
    @UnitOfWork(readOnly = true)
    @GET
    @Path("/list")
    public Response findAll() {
        log.info("Transaxtion Id: " + httpServletRequest.getAttribute("TranxId"));
        try{
            List<TweetEntity> tweets = tweetBusinessService.findAll();
            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setTweets(tweets.stream().map(tweet -> TweetDTO.tweet(tweet)).collect(Collectors.toList()));
            tweetResponse.setStatusCode(Response.Status.OK.getStatusCode());
            return Response.status(Response.Status.OK).entity(tweetResponse).build();
        } catch (Exception e) {
            log.error("Error:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Timed
    @UnitOfWork
    @PUT
    @Path("/{id}")
    public Response update(Tweet tweet, @PathParam("id") int id) {
        log.info("Transaxtion Id: " + httpServletRequest.getAttribute("TranxId"));
        try {
            tweet.setId(id);
            TweetEntity tweetEntity = tweetBusinessService.update(TweetDTO.tweetEntity(tweet));
            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setMessage("Tweet Updated successfully.");
            tweetResponse.setTweets(ImmutableList.of(TweetDTO.tweet(tweetEntity)));
            tweetResponse.setStatusCode(Response.Status.OK.getStatusCode());
            return Response.status(Response.Status.OK).entity(tweetResponse)
                    .build();
        } catch (Exception e) {
            log.error("Error:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Timed
    @UnitOfWork
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        log.info("Transaxtion Id: " + httpServletRequest.getAttribute("TranxId"));
        try {
            tweetBusinessService.delete(id);
            TweetResponse tweetResponse = new TweetResponse();
            tweetResponse.setMessage("Tweet deleted successfully.");
            tweetResponse.setStatusCode(Response.Status.OK.getStatusCode());
            return Response.status(Response.Status.OK).entity(tweetResponse)
                    .build();
        } catch (Exception e) {
            log.error("Error:", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }
    }
}
