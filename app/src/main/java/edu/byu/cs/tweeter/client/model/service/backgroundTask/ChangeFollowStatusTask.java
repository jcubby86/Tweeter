package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.ChangeFollowStatusRequest;

public abstract class ChangeFollowStatusTask<T extends ChangeFollowStatusRequest> extends AuthorizedTask<T> {

    private static final String LOG_TAG = "ChangeFollowStatusTask";

    public ChangeFollowStatusTask(T request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
    }
}
