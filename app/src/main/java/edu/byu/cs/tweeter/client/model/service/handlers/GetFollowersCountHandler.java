package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class GetFollowersCountHandler extends CountTaskHandler<FollowService.GetFollowersDataObserver> {

    public GetFollowersCountHandler(FollowService.GetFollowersDataObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers count";
    }

}
