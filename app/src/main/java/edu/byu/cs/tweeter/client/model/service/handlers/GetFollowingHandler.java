package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends PagedHandler<User> {

    public GetFollowingHandler(PagedTaskObserver<User> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get following";
    }
}
