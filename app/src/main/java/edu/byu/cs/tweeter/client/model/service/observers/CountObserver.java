package edu.byu.cs.tweeter.client.model.service.observers;

public interface CountObserver extends ServiceObserver {
    void handleSuccess(int count);
}
