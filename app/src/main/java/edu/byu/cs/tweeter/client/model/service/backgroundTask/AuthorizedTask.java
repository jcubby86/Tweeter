package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.AuthorizedRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public abstract class AuthorizedTask<REQUEST extends AuthorizedRequest, RESPONSE extends Response> extends BackgroundTask<REQUEST, RESPONSE> {

    private static final String LOG_TAG = "AuthorizedTask";

    public AuthorizedTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }

}
