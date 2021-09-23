package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class ChangeFollowStatusTask extends AuthorizedTask{

    private static final String LOG_TAG = "ChangeFollowStatusTask";
    /**
     * The user that is being followed.
     */
    protected User followee;

    public ChangeFollowStatusTask(Handler messageHandler, AuthToken authToken, User followee) {
        super(messageHandler, authToken);
        this.followee = followee;
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
    }
}
