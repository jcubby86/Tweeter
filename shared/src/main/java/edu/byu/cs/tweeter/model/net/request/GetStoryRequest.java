package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStoryRequest extends PagedRequest<Status>{
    private GetStoryRequest() {
    }

    public GetStoryRequest(AuthToken authToken, User targetUser, int limit, Status lastItem) {
        super(authToken, targetUser, limit, lastItem);
    }
}
