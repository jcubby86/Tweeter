package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends ChangeFollowStatusTask<FollowRequest, FollowResponse> {
    private static final String LOG_TAG = "FollowTask";

    public FollowTask(FollowRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected FollowResponse error(String message) {
        return new FollowResponse("Failed to follow" + message);
    }

    @Override
    protected FollowResponse runTask(FollowRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().follow(request);
    }

}
