package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthorizedTask<LogoutRequest, LogoutResponse> {
    private static final String LOG_TAG = "LogoutTask";

    public LogoutTask(LogoutRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected LogoutResponse error(String message) {
        return new LogoutResponse("Failed to logout" + message);
    }

    @Override
    protected LogoutResponse runTask(LogoutRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().logout(request);
    }

}
