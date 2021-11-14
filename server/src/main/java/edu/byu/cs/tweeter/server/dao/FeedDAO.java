package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public interface FeedDAO {
    List<Status> getFeed(String alias, int pageSize, Status lastStatus);
    void postToFollowers(Status status);
}
