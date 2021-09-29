package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;

public abstract class DataTaskHandler<T> extends BackgroundTaskHandler<DataTaskObserver<T>>{
    public DataTaskHandler(DataTaskObserver<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        T data = getData(bundle);
        observer.handleSuccess(data);
    }

    protected abstract T getData(Bundle bundle);
}
