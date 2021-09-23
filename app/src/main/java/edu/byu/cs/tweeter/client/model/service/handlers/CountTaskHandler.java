package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.observers.CountObserver;

public abstract class CountTaskHandler<T extends CountObserver> extends BackgroundTaskHandler<CountObserver>{
    public CountTaskHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        int count = bundle.getInt(CountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}
