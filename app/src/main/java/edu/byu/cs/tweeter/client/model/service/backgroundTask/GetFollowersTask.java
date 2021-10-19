package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask<GetFollowersRequest, GetFollowersResponse> {
    private static final String LOG_TAG = "GetFollowersTask";

    public GetFollowersTask(GetFollowersRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetFollowersResponse getResponse(List<User> items, boolean hasMorePages) {
        return new GetFollowersResponse(items, hasMorePages);
    }

    @Override
    protected GetFollowersResponse error(String message) {
        return new GetFollowersResponse("Failed to get followers" + message);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems(GetFollowersRequest request) {
        return getFakeData().getPageOfUsers(request.getLastItem(), request.getLimit(), request.getTargetUser());
    }

}