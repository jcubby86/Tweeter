package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class PagedRequest<DATA> extends AuthorizedRequest{
    /**
     * The user whose following is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private String targetUserAlias;
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

    public PagedRequest(AuthToken authToken, String targetUserAlias, int limit, DATA lastItem) {
        super(authToken);
        this.targetUserAlias = targetUserAlias;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }
    public void setTargetUserAlias(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
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

    @Override
    public void checkRequest() {
        super.checkRequest();
        if (targetUserAlias == null){
            badRequest();
        }
    }
}
