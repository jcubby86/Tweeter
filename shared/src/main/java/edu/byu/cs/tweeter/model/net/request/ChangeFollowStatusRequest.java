package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class ChangeFollowStatusRequest extends AuthorizedRequest{
    /**
     * The user that is being followed.
     */
    private User followee;

    protected ChangeFollowStatusRequest(){
    }

    public ChangeFollowStatusRequest(AuthToken authToken, User followee) {
        super(authToken);
        this.followee = followee;
    }

    public User getFollowee() {
        return followee;
    }
    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
