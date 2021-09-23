package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class LogoutHandler extends SimpleNotificationHandler<UserService.LogoutObserver> {


    public LogoutHandler(UserService.LogoutObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to logout";
    }
}
