package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends ChangeFollowStatusTask<UnfollowRequest> {
    private static final String LOG_TAG = "UnfollowTask";

    public UnfollowTask(UnfollowRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void runTask() {
    }
}
