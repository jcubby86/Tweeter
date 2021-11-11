package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class DynamoDBStoryDAO extends DynamoDBStatusDAO implements StoryDAO{
    private static final String TABLE_NAME = "story";

    private final Table table = getTable(TABLE_NAME);

    @Override
    public List<Status> getStory(String alias, int pageSize, Status lastStatus) {
        return doQuery(table, alias, pageSize, lastStatus);
    }

    @Override
    public void postToStories(Status status) {
        List<String> targetAliases = new ArrayList<>(status.getMentions());
        targetAliases.add(status.getUser().getAlias());
        doWrite(TABLE_NAME, status, targetAliases);
    }

}
