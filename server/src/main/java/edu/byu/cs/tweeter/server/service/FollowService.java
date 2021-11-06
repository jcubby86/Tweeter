package edu.byu.cs.tweeter.server.service;

import java.util.List;

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
        String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
        List<String> aliases = getFollowDao().getFollowing(request.getTargetUserAlias(),
                request.getLimit(), last);
        List<User> users = getUserDao().getUserList(aliases);

        return new GetFollowingResponse(users, users.size() > 0);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request){
        request.checkRequest();
        String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
        List<String> aliases = getFollowDao().getFollowers(request.getTargetUserAlias(),
                request.getLimit(), last);
        List<User> users = getUserDao().getUserList(aliases);

        return new GetFollowersResponse(users, users.size() > 0);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        request.checkRequest();
        return new IsFollowerResponse(getFollowDao().isFollower(request.getFollower(), request.getFollowee()));
    }

    public FollowResponse follow(FollowRequest request) {
        request.checkRequest();
        getFollowDao().putItem(request.getFollowerAlias(), request.getFolloweeAlias());
        return new FollowResponse();
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        request.checkRequest();
        getFollowDao().deleteItem(request.getFollowerAlias(), request.getFolloweeAlias());
        return new UnfollowResponse();
    }

    public GetCountResponse getCount(GetCountRequest request) {
        request.checkRequest();
        Pair<Integer, Integer> data = getFollowDao().getCount(request.getTargetUserAlias());
        return new GetCountResponse(data.getFirst(), data.getSecond());
    }

}
