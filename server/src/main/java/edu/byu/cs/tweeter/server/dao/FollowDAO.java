package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.server.util.Pair;

public interface FollowDAO {

    Pair<List<String>, Boolean> getFollowing(String follower_handle, int pageSize, String lastFolloweeAlias);

    Pair<List<String>, Boolean> getFollowers(String followee_handle, int pageSize, String lastFollowerAlias);

    Pair<Integer, Integer> getCount(GetCountRequest request);

    void follow(FollowRequest request);

    void unfollow(UnfollowRequest request);

    boolean isFollower(IsFollowerRequest request);
}
