package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetCountRequest extends AuthorizedRequest{

    /**
     * The user whose follower count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected String targetUserAlias;

    private GetCountRequest() {
    }

    public GetCountRequest(AuthToken authToken, String targetUserAlias) {
        super(authToken);
        this.targetUserAlias = targetUserAlias;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }
    public void setTargetUserAlias(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
    }

    @Override
    public void checkRequest() {
        super.checkRequest();
        if (targetUserAlias == null){
            badRequest();
        }
    }
}
