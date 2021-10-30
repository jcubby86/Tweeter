package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.model.net.response.Response;

public class BackgroundTaskHandler<RESPONSE extends Response> extends Handler {

    protected BackgroundTaskObserver<RESPONSE> observer;

    public BackgroundTaskHandler(Looper looper, BackgroundTaskObserver<RESPONSE> observer){
        super(looper);
        this.observer = observer;
    }

    public BackgroundTaskHandler(BackgroundTaskObserver<RESPONSE> observer) {
        super((Callback) null);
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        @SuppressWarnings("unchecked")
        RESPONSE response = (RESPONSE) msg.getData().getSerializable(BackgroundTask.RESPONSE_KEY);
        boolean success = response.isSuccess();
        if (success) {
            observer.handleSuccess(response);
        } else {
            observer.handleFailure(response.getMessage());
        }
    }
}
