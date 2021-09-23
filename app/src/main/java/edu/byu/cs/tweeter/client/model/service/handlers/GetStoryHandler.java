package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends Handler {
    private final StatusService.GetStoryObserver observer;

    public GetStoryHandler(StatusService.GetStoryObserver observer){
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
        if (success) {
            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.ITEMS_KEY);
            boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);

            observer.handleSuccess(statuses, hasMorePages);
        } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
            String message = "Failed to get story: " + msg.getData().getString(GetStoryTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
            String message = "Failed to get story because of exception: " + ex.getMessage();
            observer.handleFailure(message);
        }
    }
}