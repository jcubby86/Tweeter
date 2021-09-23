package edu.byu.cs.tweeter.client.model.service.observers;

public interface SimpleNotificationObserver extends BackgroundTaskObserver {
    void handleSuccess();
}
