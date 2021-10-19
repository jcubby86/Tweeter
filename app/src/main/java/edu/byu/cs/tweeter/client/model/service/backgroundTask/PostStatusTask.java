package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthorizedTask<PostStatusRequest, PostStatusResponse> {
    private static final String LOG_TAG = "PostStatusTask";

    public PostStatusTask(PostStatusRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected PostStatusResponse error(String message) {
        return new PostStatusResponse("Failed to post status" + message);
    }

    @Override
    protected PostStatusResponse runTask(PostStatusRequest request) throws IOException {
        return new PostStatusResponse();
    }

}
