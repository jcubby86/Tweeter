package edu.byu.cs.tweeter.client.model.service.observers;

public interface DataTaskObserver<T> extends BackgroundTaskObserver {
    void handleSuccess(T count);
}
