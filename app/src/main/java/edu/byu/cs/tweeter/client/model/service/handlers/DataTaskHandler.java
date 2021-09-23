package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;

public abstract class DataTaskHandler<D, T extends DataTaskObserver<D>> extends BackgroundTaskHandler<DataTaskObserver<D>>{
    public DataTaskHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        D data = getData(bundle);
        observer.handleSuccess(data);
    }

    protected abstract D getData(Bundle bundle);
}
