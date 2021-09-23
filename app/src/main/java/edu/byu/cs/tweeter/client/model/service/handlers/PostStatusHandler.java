package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;

public class PostStatusHandler extends Handler {
    private final StatusService.PostStatusObserver observer;

    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
        if (success) {
            observer.handleSuccess();
        } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
            String message = "Failed to post status: " + msg.getData().getString(PostStatusTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
            String message = "Failed to post status because of exception: " + ex.getMessage();
            observer.handleFailure(message);
        }
    }
}
