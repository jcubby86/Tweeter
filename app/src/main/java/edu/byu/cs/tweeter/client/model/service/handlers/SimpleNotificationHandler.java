package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;

public abstract class SimpleNotificationHandler<T extends SimpleNotificationObserver> extends BackgroundTaskHandler<SimpleNotificationObserver> {

    public SimpleNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        observer.handleSuccess();
    }
}
