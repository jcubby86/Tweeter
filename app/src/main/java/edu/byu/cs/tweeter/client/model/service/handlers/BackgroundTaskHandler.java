package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.observers.BackgroundTaskObserver;

public abstract class BackgroundTaskHandler<T extends BackgroundTaskObserver> extends Handler{

    protected T observer;

    public BackgroundTaskHandler(T observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
        if (success) {
            handleSuccess(msg.getData());
        } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
            String message = getMessage() + ": " + msg.getData().getString(FollowTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
            String message = getMessage() + " because of exception: " + ex.getMessage();
            observer.handleFailure(message);
        }
    }

    protected abstract String getMessage();

    protected abstract void handleSuccess(Bundle bundle);
}
