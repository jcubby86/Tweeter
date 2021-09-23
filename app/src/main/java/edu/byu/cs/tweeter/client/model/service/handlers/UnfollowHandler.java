package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class UnfollowHandler extends SimpleNotificationHandler<FollowService.UnfollowObserver> {


    public UnfollowHandler(FollowService.UnfollowObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to unfollow";
    }
}
