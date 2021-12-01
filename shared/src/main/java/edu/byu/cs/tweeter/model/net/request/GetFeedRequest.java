package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedRequest extends PagedRequest {
    private GetFeedRequest() {
    }

    public GetFeedRequest(AuthToken authToken, String targetUserAlias, int limit, String lastItem) {
        super(authToken, targetUserAlias, limit, lastItem);
    }

}
