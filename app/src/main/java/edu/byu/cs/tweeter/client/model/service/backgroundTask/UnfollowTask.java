package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends ChangeFollowStatusTask<UnfollowRequest, UnfollowResponse> {
    private static final String LOG_TAG = "UnfollowTask";

    public UnfollowTask(UnfollowRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected UnfollowResponse error(String message) {
        return new UnfollowResponse("Failed to unfollow" + message);
    }

    @Override
    protected UnfollowResponse runTask(UnfollowRequest request) throws IOException {
        return new UnfollowResponse();
    }

}
