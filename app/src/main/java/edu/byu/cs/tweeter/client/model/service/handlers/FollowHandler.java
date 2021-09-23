package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class FollowHandler extends SimpleNotificationHandler<FollowService.FollowObserver> {

    public FollowHandler(FollowService.FollowObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to follow";
    }
}
