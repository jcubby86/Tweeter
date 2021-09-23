package edu.byu.cs.tweeter.client.model.service.observers;

public interface DataObserver<T> extends ServiceObserver {
    void handleSuccess(T count);
}
