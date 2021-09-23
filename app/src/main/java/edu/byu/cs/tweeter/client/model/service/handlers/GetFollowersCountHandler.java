package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class GetFollowersCountHandler extends CountTaskHandler<FollowService.GetFollowersCountObserver> {

    public GetFollowersCountHandler(FollowService.GetFollowersCountObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers count";
    }
}
