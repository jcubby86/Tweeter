package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusService extends Service{
    public GetStoryResponse getStory(GetStoryRequest request){
        request.checkRequest();
        Pair<List<Status>, Boolean> data = getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
        return new GetStoryResponse(data.getFirst(), data.getSecond());
    }

    public GetFeedResponse getFeed(GetFeedRequest request){
        request.checkRequest();
        Pair<List<Status>, Boolean> data = getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
        return new GetFeedResponse(data.getFirst(), data.getSecond());
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        request.checkRequest();
        return new PostStatusResponse();
    }
}
