package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends DataTaskHandler<User, UserService.GetUserObserver> {


    public GetUserHandler(UserService.GetUserObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get user's profile";
    }

    @Override
    protected User getData(Bundle bundle) {
        return (User) bundle.getSerializable(GetUserTask.USER_KEY);
    }


}
