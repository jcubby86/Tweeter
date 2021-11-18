package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class ChangeFollowStatusRequest extends AuthorizedRequest{
    /**
     * The user that is being followed.
     */
    private String followeeAlias;

    protected ChangeFollowStatusRequest(){
    }

    public ChangeFollowStatusRequest(AuthToken authToken, String followeeAlias) {
        super(authToken);
        this.followeeAlias = followeeAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }
    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    @Override
    public void checkRequest() {
        super.checkRequest();
        if (followeeAlias == null){
            badRequest();
        }
    }
}
