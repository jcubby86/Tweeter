package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UnfollowRequest extends ChangeFollowStatusRequest{

    private UnfollowRequest() {
    }

    public UnfollowRequest(AuthToken authToken, String followeeAlias) {
        super(authToken, followeeAlias);
    }

}
