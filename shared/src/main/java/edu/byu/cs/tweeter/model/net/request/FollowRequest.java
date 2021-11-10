package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest extends ChangeFollowStatusRequest{

    private FollowRequest(){}

    public FollowRequest(AuthToken authToken, String followerAlias, String followeeAlias) {
        super(authToken, followerAlias, followeeAlias);
    }

}
