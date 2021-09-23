package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;

public class GetFollowersCountHandler extends DataTaskHandler<Integer, FollowService.GetFollowersDataObserver> {

    public GetFollowersCountHandler(FollowService.GetFollowersDataObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers count";
    }

    @Override
    protected Integer getData(Bundle bundle) {
        return bundle.getInt(CountTask.COUNT_KEY);
    }
}
