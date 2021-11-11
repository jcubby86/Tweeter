package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.util.Pair;

public interface FollowDAO {

    List<User> getFollowingPaged(String follower_handle, int pageSize, String lastFolloweeAlias);

    List<User> getFollowersPaged(String followee_handle, int pageSize, String lastFollowerAlias);

    List<String> getFollowing(String follower_handle);

    List<String> getFollowers(String followee_handle);

    Pair<Integer, Integer> getCount(String target_handle);

    void putItem(String follower_handle, String followee_handle);

    void deleteItem(String follower_handle, String followee_handle);

    boolean isFollower(String follower_handle, String followee_handle);
}
