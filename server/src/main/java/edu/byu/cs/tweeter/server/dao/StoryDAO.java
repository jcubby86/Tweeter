package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public interface StoryDAO {
    List<Status> getStory(String alias, int pageSize, Status lastStatus);
    void postToStories(Status status);
}
