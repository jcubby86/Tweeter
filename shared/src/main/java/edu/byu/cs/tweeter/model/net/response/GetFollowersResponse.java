package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersResponse extends PagedResponse<User>{

    private GetFollowersResponse() {
    }

    public GetFollowersResponse(String message) {
        super(message);
    }

    public GetFollowersResponse(List<User> items, boolean hasMorePages) {
        super(items, hasMorePages);
    }
}
