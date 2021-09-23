package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observers.AuthenticationObserver;

public class LoginHandler extends AuthenticationTaskHandler<UserService.LoginObserver> {


    public LoginHandler(UserService.LoginObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to login";
    }
}
