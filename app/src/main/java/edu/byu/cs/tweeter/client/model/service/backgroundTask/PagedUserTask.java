package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public abstract class PagedUserTask<REQUEST extends PagedRequest<User>, RESPONSE extends PagedResponse<User>> extends PagedTask<User, REQUEST, RESPONSE> {

    private static final String LOG_TAG = "PagedUserTask";

    public PagedUserTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected User convertItemToUser(User item) {
        return item;
    }
}
