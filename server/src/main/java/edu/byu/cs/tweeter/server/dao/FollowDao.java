package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.server.util.Pair;

public interface FollowDao {

    Pair<List<String>, Boolean> getFollowing(String follower_handle, int pageSize, String lastFolloweeAlias);

    Pair<List<String>, Boolean> getFollowers(String followee_handle, int pageSize, String lastFollowerAlias);

    void putItem(String follower_handle, String followee_handle, String follower_name, String followee_name) throws DataAccessException;

    void deleteItem(String follower_handle, String followee_handle) throws DataAccessException;
}
