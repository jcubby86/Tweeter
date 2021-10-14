package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.util.Random;

import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthorizedTask<IsFollowerRequest> {
    private static final String LOG_TAG = "IsFollowerTask";

    public static final String IS_FOLLOWER_KEY = "is-follower";

    private boolean isFollower;

    public IsFollowerTask(IsFollowerRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }

    @Override
    protected void runTask() {
        isFollower = new Random().nextInt() > 0;
    }
}
