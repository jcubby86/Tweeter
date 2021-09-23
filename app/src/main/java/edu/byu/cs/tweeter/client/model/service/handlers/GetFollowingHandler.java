package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends BackgroundTaskHandler<FollowService.GetFollowingObserver> {


    public GetFollowingHandler(FollowService.GetFollowingObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get following";
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        List<User> followees = (List<User>) bundle.getSerializable(GetFollowingTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        observer.handleSuccess(followees, hasMorePages);
    }
}
