package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthorizedTask extends BackgroundTask{

    private static final String LOG_TAG = "AuthorizedTask";

    /**
     * Auth token for logged-in user.
     */
    protected AuthToken authToken;

    public AuthorizedTask(Handler messageHandler, AuthToken authToken) {
        super(messageHandler);
        this.authToken = authToken;
    }

}
