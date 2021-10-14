package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.LogoutRequest;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthorizedTask<LogoutRequest> {
    private static final String LOG_TAG = "LogoutTask";

    public LogoutTask(LogoutRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {

    }

    @Override
    protected void runTask() {

    }
}
