package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationResponse extends Response{

    protected User user;
    protected AuthToken authToken;

    protected AuthenticationResponse(){}

    public AuthenticationResponse(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public AuthenticationResponse(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
