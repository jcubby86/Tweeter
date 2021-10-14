package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.CountRequest;

public class CountTask extends AuthorizedTask<CountRequest> {

    private static final String LOG_TAG = "AuthorizedTask";

    public static final String FOLLOWERS_COUNT_KEY = "followers";
    public static final String FOLLOWING_COUNT_KEY = "following";

    private int followersCount;
    private int followingCount;

    public CountTask(CountRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void runTask() {
        followersCount = 20;
        followingCount = 20;
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putInt(FOLLOWERS_COUNT_KEY, followersCount);
        msgBundle.putInt(FOLLOWING_COUNT_KEY, followingCount);
    }
}
