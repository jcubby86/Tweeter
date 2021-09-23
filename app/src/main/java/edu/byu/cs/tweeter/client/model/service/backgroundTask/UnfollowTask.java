package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends ChangeFollowStatusTask {
    private static final String LOG_TAG = "UnfollowTask";

    public UnfollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler, authToken, followee);
    }

    @Override
    protected void runTask() {
    }
}
