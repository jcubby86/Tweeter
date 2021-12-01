package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetFollowersRequest extends PagedRequest {
    private GetFollowersRequest() {
    }

    public GetFollowersRequest(AuthToken authToken, String targetUserAlias, int limit, String lastItem) {
        super(authToken, targetUserAlias, limit, lastItem);
    }

}
