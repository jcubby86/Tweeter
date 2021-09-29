package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends AuthenticationTaskHandler{

    public RegisterHandler(DataTaskObserver<User> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to register";
    }
}
