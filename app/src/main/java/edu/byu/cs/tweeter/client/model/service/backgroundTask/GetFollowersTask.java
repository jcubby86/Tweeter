package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask<GetFollowersRequest, GetFollowersResponse> {
    private static final String LOG_TAG = "GetFollowersTask";

    public GetFollowersTask(GetFollowersRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetFollowersResponse getResponse(GetFollowersRequest request) {
        return getFollowService().getFollowers(request);
    }

    @Override
    protected GetFollowersResponse error(String message) {
        return new GetFollowersResponse("Failed to get followers" + message);
    }


}