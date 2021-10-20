package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedRequest extends PagedRequest<Status>{
    private GetFeedRequest() {
    }

    public GetFeedRequest(AuthToken authToken, String targetUserAlias, int limit, Status lastItem) {
        super(authToken, targetUserAlias, limit, lastItem);
    }
}
