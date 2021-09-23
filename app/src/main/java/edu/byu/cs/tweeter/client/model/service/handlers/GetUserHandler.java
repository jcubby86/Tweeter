package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends BackgroundTaskHandler<UserService.GetUserObserver> {


    public GetUserHandler(UserService.GetUserObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get user's profile";
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        User user = (User) bundle.getSerializable(GetUserTask.USER_KEY);
        observer.handleSuccess(user);
    }


}
