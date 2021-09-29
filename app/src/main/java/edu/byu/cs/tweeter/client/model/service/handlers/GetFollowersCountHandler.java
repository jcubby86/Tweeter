package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;

public class GetFollowersCountHandler extends CountTaskHandler{

    public GetFollowersCountHandler(DataTaskObserver<Integer> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers count";
    }

}
