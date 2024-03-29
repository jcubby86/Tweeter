package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticationTask<RegisterRequest, RegisterResponse> {
    private static final String LOG_TAG = "RegisterTask";

    public RegisterTask(RegisterRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected RegisterResponse error(String message) {
        return new RegisterResponse("Failed to register" + message);
    }

    @Override
    protected RegisterResponse doAuthenticate(RegisterRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().register(request);
    }
}
