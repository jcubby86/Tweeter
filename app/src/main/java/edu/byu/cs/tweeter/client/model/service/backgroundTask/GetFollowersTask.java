package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask<GetFollowersRequest> {
    private static final String LOG_TAG = "GetFollowersTask";

    public GetFollowersTask(GetFollowersRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        return getFakeData().getPageOfUsers(request.getLastItem(), request.getLimit(), request.getTargetUser());
    }

}