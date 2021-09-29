package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;

public class GetFollowingCountHandler extends CountTaskHandler {

    public GetFollowingCountHandler(DataTaskObserver<Integer> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get following count";
    }
}
