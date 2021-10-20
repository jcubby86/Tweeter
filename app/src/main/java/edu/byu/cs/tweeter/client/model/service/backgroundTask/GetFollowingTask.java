package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask<GetFollowingRequest, GetFollowingResponse> {
    private static final String LOG_TAG = "GetFollowingTask";

    public GetFollowingTask(GetFollowingRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetFollowingResponse getResponse(GetFollowingRequest request) {
        return getFollowService().getFollowing(request);
    }

    @Override
    protected GetFollowingResponse error(String message) {
        return new GetFollowingResponse("Failed to get following" + message);
    }

}
