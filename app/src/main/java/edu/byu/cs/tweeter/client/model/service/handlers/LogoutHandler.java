package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;

public class LogoutHandler extends SimpleNotificationHandler {

    public LogoutHandler(SimpleNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to logout";
    }
}
