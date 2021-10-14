package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.BackgroundTaskUtils;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticationRequest;
import edu.byu.cs.tweeter.util.Pair;

public abstract class AuthenticationTask<T extends AuthenticationRequest> extends BackgroundTask<T> {

    private static final String LOG_TAG = "AuthenticationTask";

    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    protected User authenticatedUser;
    protected AuthToken authToken;

    public AuthenticationTask(T request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void runTask() throws IOException {
        Pair<User, AuthToken> result = doAuthenticate();

        authenticatedUser = result.getFirst();
        authToken = result.getSecond();

        BackgroundTaskUtils.loadImage(authenticatedUser);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, authenticatedUser);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }

    protected abstract Pair<User, AuthToken> doAuthenticate();
}
