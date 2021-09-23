package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class RegisterHandler extends AuthenticationTaskHandler<UserService.RegisterObserver> {


    public RegisterHandler(UserService.RegisterObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to register";
    }
}
