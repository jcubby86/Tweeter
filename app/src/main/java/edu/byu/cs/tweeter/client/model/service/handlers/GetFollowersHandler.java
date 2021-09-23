package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetFollowersTask.
 */
public class GetFollowersHandler extends PagedHandler<User, FollowService.GetFollowersObserver> {

    public GetFollowersHandler(FollowService.GetFollowersObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers";
    }

}
