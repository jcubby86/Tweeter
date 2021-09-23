package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends BackgroundTaskHandler<FollowService.IsFollowerObserver> {

    public IsFollowerHandler(FollowService.IsFollowerObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to determine following relationship";
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        boolean isFollower = bundle.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.handleSuccess(isFollower);
    }
}
