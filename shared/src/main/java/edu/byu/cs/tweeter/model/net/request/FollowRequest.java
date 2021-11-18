package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowRequest extends ChangeFollowStatusRequest{

    private FollowRequest(){}

    public FollowRequest(AuthToken authToken, String followeeAlias) {
        super(authToken, followeeAlias);
    }

}
