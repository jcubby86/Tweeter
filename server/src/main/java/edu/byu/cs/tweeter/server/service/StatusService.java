package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.DAOFactory;

public class StatusService extends Service{

    public StatusService(DAOFactory daoFactory, LambdaLogger logger) {
        super(daoFactory, logger);
    }

    public GetStoryResponse getStory(GetStoryRequest request){
        if (isAuthorized(request)) {
            List<Status> statuses = getStoryDAO().getStory(request.getTargetUserAlias(), request.getLimit(), request.getLastItem());
            logger.log("Story retrieved for user: " + request.getTargetUserAlias());
            return new GetStoryResponse(statuses, statuses.size() > 0);
        } else {
            logUnauthorized(request);
            return new GetStoryResponse(NOT_AUTHORIZED);
        }
    }

    public GetFeedResponse getFeed(GetFeedRequest request){
        if (isAuthorized(request)) {
            List<Status> statuses = getFeedDAO().getFeed(request.getTargetUserAlias(), request.getLimit(), request.getLastItem());
            logger.log("Feed retrieved for user: " + request.getTargetUserAlias());
            return new GetFeedResponse(statuses, statuses.size() > 0);
        } else {
            logUnauthorized(request);
            return new GetFeedResponse(NOT_AUTHORIZED);
        }
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (isAuthorized(request)) {
            getFeedDAO().postToFollowers(request.getStatus());
            getStoryDAO().postToStories(request.getStatus());
            logger.log("Status posted by user: " + request.getStatus().getUser().getAlias());
            return new PostStatusResponse();
        } else {
            logUnauthorized(request);
            return new PostStatusResponse(NOT_AUTHORIZED);
        }
    }

}
