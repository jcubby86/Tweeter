package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryResponse extends PagedResponse<Status>{

    private GetStoryResponse() {
    }

    public GetStoryResponse(String message) {
        super(message);
    }

    public GetStoryResponse(List<Status> items, boolean hasMorePages) {
        super(items, hasMorePages);
    }
}
