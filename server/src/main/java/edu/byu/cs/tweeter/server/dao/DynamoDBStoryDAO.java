package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class DynamoDBStoryDAO extends DynamoDBStatusDAO implements StoryDAO{
    private static final String TABLE_NAME = "story";

    public DynamoDBStoryDAO(DAOFactory factory) {
        super(factory, TABLE_NAME);
    }

    @Override
    public List<Status> getStory(String alias, int pageSize, Status lastStatus) {
        return doQuery(alias, pageSize, lastStatus);
    }

    @Override
    public void postToStories(Status status) {
        List<String> targetAliases = new ArrayList<>(status.getMentions());
        targetAliases.add(status.getUser().getAlias());
        doWrite(status, targetAliases);
    }

}
