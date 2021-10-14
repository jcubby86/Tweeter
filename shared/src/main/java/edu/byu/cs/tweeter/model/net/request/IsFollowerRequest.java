package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class IsFollowerRequest extends AuthorizedRequest{
    /**
     * The alleged follower.
     */
    private User follower;
    /**
     * The alleged followee.
     */
    private User followee;

    private IsFollowerRequest() {
    }

    public IsFollowerRequest(AuthToken authToken, User follower, User followee) {
        super(authToken);
        this.follower = follower;
        this.followee = followee;
    }

    public User getFollower() {
        return follower;
    }
    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }
    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
