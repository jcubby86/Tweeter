package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.server.util.Pair;

public interface FollowDAO {

    Pair<List<String>, Boolean> getFollowingPaged(String follower_handle, int pageSize, String lastFolloweeAlias);

    Pair<List<String>, Boolean> getFollowersPaged(String followee_handle, int pageSize, String lastFollowerAlias);

    List<String> getFollowing(String follower_handle);

    List<String> getFollowers(String followee_handle);

    Pair<Integer, Integer> getCount(String target_handle);

    void follow(FollowRequest request);

    void unfollow(UnfollowRequest request);

    boolean isFollower(String follower_handle, String followee_handle);
}
