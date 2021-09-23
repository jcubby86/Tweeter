package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class GetFollowingCountHandler extends CountTaskHandler<FollowService.GetFollowingCountObserver> {

    public GetFollowingCountHandler(FollowService.GetFollowingCountObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get following count";
    }
}
