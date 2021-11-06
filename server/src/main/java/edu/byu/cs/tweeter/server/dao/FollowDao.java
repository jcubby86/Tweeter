package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.server.util.Pair;

public interface FollowDao {

    List<String> getFollowing(String follower_handle, int pageSize, String lastFolloweeAlias);

    List<String> getFollowers(String followee_handle, int pageSize, String lastFollowerAlias);

    Pair<Integer, Integer> getCount(String target_handle);

    void putItem(String follower_handle, String followee_handle) throws DataAccessException;

    void deleteItem(String follower_handle, String followee_handle) throws DataAccessException;

    boolean isFollower(String follower_handle, String followee_handle) throws DataAccessException;
}
