package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthorizedTask<GetUserRequest> {

    private static final String LOG_TAG = "GetUserTask";

    public static final String USER_KEY = "user";

    private User user;

    public GetUserTask(GetUserRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    private User getUser() {
        return getFakeData().findUserByAlias(request.getAlias());
    }

    @Override
    protected void runTask() {
        user = getUser();
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
    }
}
