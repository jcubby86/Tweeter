package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest extends AuthorizedRequest{
    private LogoutRequest() {
    }

    public LogoutRequest(AuthToken authToken) {
        super(authToken);
    }
}
