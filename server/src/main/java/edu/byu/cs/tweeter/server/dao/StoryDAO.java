package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.util.Pair;

public interface StoryDAO {
    Pair<List<Status>, Boolean> getStory(String alias, int pageSize, Status lastStatus);
    void postToStories(Status status);
}
