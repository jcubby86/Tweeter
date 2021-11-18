package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class IsFollowerRequest extends AuthorizedRequest{

    /**
     * The alleged followee.
     */
    private String followee;

    private IsFollowerRequest() {
    }

    public IsFollowerRequest(AuthToken authToken, String followee) {
        super(authToken);
        this.followee = followee;
    }

    public String getFollowee() {
        return followee;
    }
    public void setFollowee(String followee) {
        this.followee = followee;
    }

    @Override
    public void checkRequest() {
        super.checkRequest();
        if (followee == null){
            badRequest();
        }
    }
}
