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
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusService extends Service{

    public StatusService(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public GetStoryResponse getStory(GetStoryRequest request){
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetStoryResponse("User not authorized");
        Pair<List<Status>, Boolean> data = getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
        return new GetStoryResponse(data.getFirst(), data.getSecond());
    }

    public GetFeedResponse getFeed(GetFeedRequest request){
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetFeedResponse("User not authorized");
        Pair<List<Status>, Boolean> data = getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
        return new GetFeedResponse(data.getFirst(), data.getSecond());
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new PostStatusResponse("User not authorized");
        return new PostStatusResponse();
    }
}
