package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;

public abstract class PagedStatusTask<T extends PagedRequest<Status>> extends PagedTask<Status, T> {

    private static final String LOG_TAG = "PagedStatusTask";

    public PagedStatusTask(T request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected User convertItemToUser(Status item) {
        return item.getUser();
    }
}
