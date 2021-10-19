package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.net.request.ChangeFollowStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public abstract class ChangeFollowStatusTask<REQUEST extends ChangeFollowStatusRequest, RESPONSE extends Response> extends AuthorizedTask<REQUEST, RESPONSE> {

    private static final String LOG_TAG = "ChangeFollowStatusTask";

    public ChangeFollowStatusTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }
}
