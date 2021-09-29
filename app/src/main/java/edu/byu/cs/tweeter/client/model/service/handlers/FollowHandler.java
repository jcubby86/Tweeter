package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;

public class FollowHandler extends SimpleNotificationHandler {

    public FollowHandler(SimpleNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to follow";
    }
}
