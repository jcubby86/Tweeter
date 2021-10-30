package edu.byu.cs.tweeter.server.service;

import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.util.Pair;


public class FollowService extends Service{
    public GetFollowingResponse getFollowing(GetFollowingRequest request) {
        request.checkRequest();
        Pair<List<User>, Boolean> data = getFakeData().getPageOfUsers(request.getLastItem(), request.getLimit(),
                getFakeData().findUserByAlias(request.getTargetUserAlias()));
        return new GetFollowingResponse(data.getFirst(), data.getSecond());
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request){
        request.checkRequest();
        Pair<List<User>, Boolean> data = getFakeData().getPageOfUsers(request.getLastItem(), request.getLimit(),
                getFakeData().findUserByAlias(request.getTargetUserAlias()));

        return new GetFollowersResponse(data.getFirst(), data.getSecond());
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        request.checkRequest();
        return new IsFollowerResponse(new Random().nextInt() > 0);
    }

    public FollowResponse follow(FollowRequest request) {
        request.checkRequest();
        return new FollowResponse();
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        request.checkRequest();
        return new UnfollowResponse();
    }

    public GetCountResponse getCount(GetCountRequest request) {
        request.checkRequest();
        return new GetCountResponse(20, 20);
    }

}
