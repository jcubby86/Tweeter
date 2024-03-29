package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBStoryDAO extends DynamoDBStatusDAO implements StoryDAO{

    public DynamoDBStoryDAO() {
        super(STORY_TABLE);
    }

    @Override
    public Pair<List<Status>, Boolean> getStory(String alias, int pageSize, String lastItem) {
        return doQuery(alias, pageSize, lastItem);
    }

    @Override
    public void postToStories(Status status) {
        List<String> targetAliases = new ArrayList<>(status.getMentions());
        targetAliases.add(status.getAuthor());
        doWrite(status, targetAliases);
    }

}
