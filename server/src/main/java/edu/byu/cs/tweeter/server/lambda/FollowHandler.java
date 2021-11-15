package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

public class FollowHandler extends Handler<FollowRequest, FollowResponse>{
    @Override
    public FollowResponse handleRequest(FollowRequest input, Context context) {
        return getFollowService().follow(input);
    }
}
