package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class DynamoDBFeedDAO extends DynamoDBStatusDAO implements FeedDAO{
    private static final String TABLE_NAME = "feed";

    private final Table table = getTable(TABLE_NAME);

    public DynamoDBFeedDAO(DAOFactory factory) {
        super(factory);
    }

    @Override
    public List<Status> getFeed(String alias, int pageSize, Status lastStatus) {
        return doQuery(table, alias, pageSize, lastStatus);
    }

    @Override
    public void postToFeeds(Status status, List<String> followers) {
        followers.add(status.getUser().getAlias());
        doWrite(TABLE_NAME, status, followers);
    }

}
