package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.BackgroundTaskUtils;
import edu.byu.cs.tweeter.model.net.request.AuthenticationRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticationResponse;

public abstract class AuthenticationTask<REQUEST extends AuthenticationRequest, RESPONSE extends AuthenticationResponse> extends BackgroundTask<REQUEST, RESPONSE> {

    private static final String LOG_TAG = "AuthenticationTask";

    public AuthenticationTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected RESPONSE runTask(REQUEST request) throws IOException {
        RESPONSE response = doAuthenticate(request);

        BackgroundTaskUtils.loadImage(response.getUser());

        return response;
    }

    protected abstract RESPONSE doAuthenticate(REQUEST request);
}
