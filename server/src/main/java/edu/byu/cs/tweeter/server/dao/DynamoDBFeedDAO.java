package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class DynamoDBFeedDAO extends DynamoDBStatusDAO implements FeedDAO{
    private static final String TABLE_NAME = "feed";

    public DynamoDBFeedDAO(DAOFactory factory) {
        super(factory, TABLE_NAME);
    }

    @Override
    public List<Status> getFeed(String alias, int pageSize, Status lastStatus) {
        return doQuery(alias, pageSize, lastStatus);
    }

    @Override
    public void postToFollowers(Status status) {
        List<String> followers = factory.getFollowDAO().getFollowers(status.getUser().getAlias());
        doWrite(status, followers);
    }

}
