package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryRequest extends PagedRequest {
    private GetStoryRequest() {
    }

    public GetStoryRequest(AuthToken authToken, String targetUserAlias, int limit, String lastItem) {
        super(authToken, targetUserAlias, limit, lastItem);
    }

}
