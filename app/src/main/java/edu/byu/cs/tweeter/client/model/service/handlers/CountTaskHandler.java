package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.util.Pair;

public class CountTaskHandler extends DataTaskHandler<Pair<Integer, Integer>> {

    public CountTaskHandler(DataTaskObserver<Pair<Integer, Integer>> observer) {
        super(observer);
    }

    @Override
    protected Pair<Integer, Integer> getData(Bundle bundle) {
        return new Pair<>(bundle.getInt(CountTask.FOLLOWERS_COUNT_KEY), bundle.getInt(CountTask.FOLLOWING_COUNT_KEY));
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers/following counts";
    }
}
