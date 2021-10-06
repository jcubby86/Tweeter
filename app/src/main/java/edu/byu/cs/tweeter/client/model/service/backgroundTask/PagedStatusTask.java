package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedStatusTask extends PagedTask<Status> {

    private static final String LOG_TAG = "PagedStatusTask";

    public PagedStatusTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, Status lastItem) {
        super(messageHandler, authToken, targetUser, limit, lastItem);
    }

    @Override
    protected User convertItemToUser(Status item) {
        return item.getUser();
    }
}
