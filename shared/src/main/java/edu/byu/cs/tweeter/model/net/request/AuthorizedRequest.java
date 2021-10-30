package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthorizedRequest extends Request{

    /**
     * Auth token for logged-in user.
     */
    private AuthToken authToken;

    protected AuthorizedRequest(){
    }

    public AuthorizedRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    @Override
    public void checkRequest() {
        if (authToken == null){
            badRequest();
        }
    }
}
