package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthorizedTask<IsFollowerRequest, IsFollowerResponse> {
    private static final String LOG_TAG = "IsFollowerTask";

    public IsFollowerTask(IsFollowerRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected IsFollowerResponse error(String message) {
        return new IsFollowerResponse("Failed to determine following relationship" + message);
    }

    @Override
    protected IsFollowerResponse runTask(IsFollowerRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().isFollower(request);
    }

}
