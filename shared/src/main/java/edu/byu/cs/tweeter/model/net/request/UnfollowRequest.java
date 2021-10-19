package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UnfollowRequest extends ChangeFollowStatusRequest{

    private UnfollowRequest() {
    }

    public UnfollowRequest(AuthToken authToken, User followee) {
        super(authToken, followee);
    }
}