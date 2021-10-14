package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask<GetFollowingRequest> {

    private static final String LOG_TAG = "GetFollowingTask";

    public GetFollowingTask(GetFollowingRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        return getFakeData().getPageOfUsers(request.getLastItem(), request.getLimit(), request.getTargetUser());
    }

}
