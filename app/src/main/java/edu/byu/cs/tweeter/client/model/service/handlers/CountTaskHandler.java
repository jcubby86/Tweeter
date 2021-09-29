package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;

public abstract class CountTaskHandler extends DataTaskHandler<Integer> {

    public CountTaskHandler(DataTaskObserver<Integer> observer) {
        super(observer);
    }

    @Override
    protected Integer getData(Bundle bundle) {
        return bundle.getInt(CountTask.COUNT_KEY);
    }
}
