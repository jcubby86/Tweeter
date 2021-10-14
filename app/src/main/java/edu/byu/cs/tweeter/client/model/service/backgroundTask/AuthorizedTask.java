package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.AuthorizedRequest;

public abstract class AuthorizedTask<T extends AuthorizedRequest> extends BackgroundTask<T> {

    private static final String LOG_TAG = "AuthorizedTask";

    public AuthorizedTask(T request, Handler messageHandler) {
        super(request, messageHandler);
    }

}
