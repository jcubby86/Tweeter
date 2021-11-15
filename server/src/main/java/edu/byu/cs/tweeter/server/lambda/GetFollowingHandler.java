package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;

public class GetFollowingHandler extends Handler<GetFollowingRequest, GetFollowingResponse>{

    @Override
    public GetFollowingResponse handleRequest(GetFollowingRequest input, Context context) {
        return getFollowService(context.getLogger()).getFollowing(input);
    }
}
