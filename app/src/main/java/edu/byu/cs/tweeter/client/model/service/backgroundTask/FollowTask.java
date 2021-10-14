package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends ChangeFollowStatusTask<FollowRequest> {
    private static final String LOG_TAG = "FollowTask";

    public FollowTask(FollowRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void runTask() {
    }
}
