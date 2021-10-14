package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthorizedTask<PostStatusRequest> {
    private static final String LOG_TAG = "PostStatusTask";


    public PostStatusTask(PostStatusRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {

    }

    @Override
    protected void runTask() {

    }
}
