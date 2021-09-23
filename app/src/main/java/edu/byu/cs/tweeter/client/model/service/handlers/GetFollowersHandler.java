package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetFollowersTask.
 */
public class GetFollowersHandler extends BackgroundTaskHandler<FollowService.GetFollowersObserver> {


    public GetFollowersHandler(FollowService.GetFollowersObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get followers";
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        List<User> followers = (List<User>) bundle.getSerializable(GetFollowersTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(GetFollowersTask.MORE_PAGES_KEY);
        observer.handleSuccess(followers, hasMorePages);
    }
}
