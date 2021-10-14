package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;

public abstract class PagedUserTask<T extends PagedRequest<User>> extends PagedTask<User, T> {

    private static final String LOG_TAG = "PagedUserTask";

    public PagedUserTask(T request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected User convertItemToUser(User item) {
        return item;
    }
}
