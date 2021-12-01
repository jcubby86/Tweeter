package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetFollowingRequest extends PagedRequest {
    private GetFollowingRequest() {
    }

    public GetFollowingRequest(AuthToken authToken, String targetUserAlias, int limit, String lastItem) {
        super(authToken, targetUserAlias, limit, lastItem);
    }

}
