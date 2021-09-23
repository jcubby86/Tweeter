package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;

public class GetFollowingCountHandler extends DataTaskHandler<Integer, FollowService.GetFollowingDataObserver> {

    public GetFollowingCountHandler(FollowService.GetFollowingDataObserver observer) {
        super(observer);
    }

    @Override
    protected Integer getData(Bundle bundle) {
        return bundle.getInt(CountTask.COUNT_KEY);
    }

    @Override
    protected String getMessage() {
        return "Failed to get following count";
    }
}
