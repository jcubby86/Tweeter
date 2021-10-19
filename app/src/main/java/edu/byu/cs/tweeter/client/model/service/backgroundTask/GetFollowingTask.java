package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask<GetFollowingRequest, GetFollowingResponse> {

    private static final String LOG_TAG = "GetFollowingTask";

    public GetFollowingTask(GetFollowingRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetFollowingResponse getResponse(List<User> items, boolean hasMorePages) {
        return new GetFollowingResponse(items, hasMorePages);
    }

    @Override
    protected GetFollowingResponse error(String message) {
        return new GetFollowingResponse("Failed to get following" + message);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems(GetFollowingRequest request) {
        return getFakeData().getPageOfUsers(request.getLastItem(), request.getLimit(), request.getTargetUser());
    }

}
