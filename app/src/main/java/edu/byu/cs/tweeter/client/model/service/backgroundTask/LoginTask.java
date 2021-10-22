package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask<LoginRequest, LoginResponse> {
    private static final String LOG_TAG = "LoginTask";

    public LoginTask(LoginRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected LoginResponse error(String message) {
        return new LoginResponse("Failed to login" + message);
    }

    @Override
    protected LoginResponse doAuthenticate(LoginRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().login(request);
    }
}
