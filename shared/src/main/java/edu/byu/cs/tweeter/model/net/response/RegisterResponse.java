package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse extends AuthenticationResponse{

    private RegisterResponse(){}

    public RegisterResponse(User user, AuthToken authToken) {
        super(user, authToken);
    }

    public RegisterResponse(String message) {
        super(message);
    }
}
