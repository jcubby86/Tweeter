package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.dynamodbv2.xspec.S;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.DynamoDBDAOFactory;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusService extends Service{

    public StatusService(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public GetStoryResponse getStory(GetStoryRequest request){
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetStoryResponse("User not authorized");
        List<Status> statuses = getStoryDAO().getStory(request.getTargetUserAlias(), request.getLimit(), request.getLastItem());
        return new GetStoryResponse(statuses, statuses.size() > 0);
    }

    public GetFeedResponse getFeed(GetFeedRequest request){
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetFeedResponse("User not authorized");
        List<Status> statuses = getFeedDAO().getFeed(request.getTargetUserAlias(), request.getLimit(), request.getLastItem());
        return new GetFeedResponse(statuses, statuses.size() > 0);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new PostStatusResponse("User not authorized");
        List<String> followers = getFollowDAO().getFollowers(request.getStatus().getUser().getAlias(), Integer.MAX_VALUE, null);
        getFeedDAO().postToFeeds(request.getStatus(), followers);
        getStoryDAO().postToStories(request.getStatus());

        return new PostStatusResponse();
    }

}
