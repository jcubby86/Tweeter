package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingRequest extends PagedRequest<User>{
    private GetFollowingRequest() {
    }

    public GetFollowingRequest(AuthToken authToken, User targetUser, int limit, User lastItem) {
        super(authToken, targetUser, limit, lastItem);
    }
}
