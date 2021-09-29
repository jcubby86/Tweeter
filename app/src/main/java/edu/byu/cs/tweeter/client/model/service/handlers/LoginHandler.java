package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginHandler extends AuthenticationTaskHandler {

    public LoginHandler(DataTaskObserver<User> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to login";
    }
}
