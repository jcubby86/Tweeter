package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetFollowersTask.
 */
public class GetFollowersHandler extends Handler {
    private FollowService.FollowersObserver observer;

    public GetFollowersHandler(FollowService.FollowersObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetFollowersTask.SUCCESS_KEY);
        if (success) {
            List<User> followers = (List<User>) msg.getData().getSerializable(GetFollowersTask.ITEMS_KEY);
            boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);
            observer.handleSuccess(followers, hasMorePages);

        } else if (msg.getData().containsKey(GetFollowersTask.MESSAGE_KEY)) {
            String message = "Failed to get followers: " + msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(GetFollowersTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
            String message = "Failed to get followers because of exception: " + ex.getMessage();
            observer.handleFailure(message);
        }
    }
}
