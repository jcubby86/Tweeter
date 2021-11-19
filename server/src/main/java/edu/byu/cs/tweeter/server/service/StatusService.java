package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.sqs.UpdateFeedsMessage;
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusService extends Service{

    public static final int PAGE_SIZE = 25;

    public StatusService(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public GetStoryResponse getStory(GetStoryRequest request){
        if (isAuthorized(request)) {
            Pair<List<Status>, Boolean> data = getStoryDAO().getStory(request.getTargetUserAlias(), request.getLimit(), request.getLastItem());
            System.out.println("Story retrieved for user: " + request.getTargetUserAlias());
            return new GetStoryResponse(data.getFirst(), data.getSecond());
        } else {
            logUnauthorized(request);
            return new GetStoryResponse(NOT_AUTHORIZED);
        }
    }

    public GetFeedResponse getFeed(GetFeedRequest request){
        if (isAuthorized(request)) {
            Pair<List<Status>, Boolean> data = getFeedDAO().getFeed(request.getTargetUserAlias(), request.getLimit(), request.getLastItem());
            System.out.println("Feed retrieved for user: " + request.getTargetUserAlias());
            return new GetFeedResponse(data.getFirst(), data.getSecond());
        } else {
            logUnauthorized(request);
            return new GetFeedResponse(NOT_AUTHORIZED);
        }
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (isAuthorized(request)) {
            getStoryDAO().postToStories(request.getStatus());
            System.out.println("Status posted by user: " + request.getStatus().getUser().getAlias());
            return new PostStatusResponse();
        } else {
            logUnauthorized(request);
            return new PostStatusResponse(NOT_AUTHORIZED);
        }
    }

    public void sqsPostStatus(UpdateFeedsMessage message){
        getFeedDAO().postToFollowers(message.getStatus(), message.getFollowers());
    }

}
