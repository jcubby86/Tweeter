package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class CountRequest extends AuthorizedRequest{

    /**
     * The user whose follower count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected User targetUser;

    private CountRequest() {
    }

    public CountRequest(AuthToken authToken, User targetUser) {
        super(authToken);
        this.targetUser = targetUser;
    }

    public User getTargetUser() {
        return targetUser;
    }
    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
}
