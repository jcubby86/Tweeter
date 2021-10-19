package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public abstract class PagedStatusTask<REQUEST extends PagedRequest<Status>, RESPONSE extends PagedResponse<Status>> extends PagedTask<Status, REQUEST, RESPONSE> {

    private static final String LOG_TAG = "PagedStatusTask";

    public PagedStatusTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected User convertItemToUser(Status item) {
        return item.getUser();
    }
}
