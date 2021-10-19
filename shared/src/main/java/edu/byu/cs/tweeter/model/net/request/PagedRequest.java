package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedRequest<DATA> extends AuthorizedRequest{
    /**
     * The user whose following is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;
    /**
     * Maximum number of followed users to return (i.e., page size).
     */
    private int limit;
    /**
     * The last person being followed returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    private DATA lastItem;

    protected PagedRequest() {
    }

    public PagedRequest(AuthToken authToken, User targetUser, int limit, DATA lastItem) {
        super(authToken);
        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    public User getTargetUser() {
        return targetUser;
    }
    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public DATA getLastItem() {
        return lastItem;
    }
    public void setLastItem(DATA lastItem) {
        this.lastItem = lastItem;
    }
}