package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class ChangeFollowStatusRequest extends AuthorizedRequest{
    /**
     * The user that is being followed.
     */
    private String followeeAlias;
    private String followerAlias;

    protected ChangeFollowStatusRequest(){
    }

    public ChangeFollowStatusRequest(AuthToken authToken, String followerAlias, String followeeAlias) {
        super(authToken);
        this.followeeAlias = followeeAlias;
        this.followerAlias = followerAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }
    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }
    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    @Override
    public void checkRequest() {
        super.checkRequest();
        if (followeeAlias == null || followerAlias == null){
            badRequest();
        }
    }
}
