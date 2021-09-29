package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;

public class UnfollowHandler extends SimpleNotificationHandler {

    public UnfollowHandler(SimpleNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to unfollow";
    }
}
