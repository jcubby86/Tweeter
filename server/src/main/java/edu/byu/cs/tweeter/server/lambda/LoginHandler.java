package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler extends Handler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest input, Context context) {
        return getUserService(context.getLogger()).login(input);
    }

}
