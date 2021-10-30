package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest extends AuthorizedRequest{
    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private String alias;

    private GetUserRequest() {
    }

    public GetUserRequest(AuthToken authToken, String alias) {
        super(authToken);
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public void checkRequest() {
        super.checkRequest();
        if (alias == null){
            badRequest();
        }
    }
}
