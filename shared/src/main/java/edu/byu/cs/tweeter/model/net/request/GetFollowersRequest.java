package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersRequest extends PagedRequest<User>{
    private GetFollowersRequest() {
    }

    public GetFollowersRequest(AuthToken authToken, String targetUserAlias, int limit, User lastItem) {
        super(authToken, targetUserAlias, limit, lastItem);
    }

}
