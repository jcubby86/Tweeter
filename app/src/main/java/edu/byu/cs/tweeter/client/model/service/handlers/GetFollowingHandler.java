package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends PagedHandler<User, FollowService.GetFollowingObserver> {

    public GetFollowingHandler(FollowService.GetFollowingObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get following";
    }
}
