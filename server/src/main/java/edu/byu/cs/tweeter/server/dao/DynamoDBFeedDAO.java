package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBFeedDAO extends DynamoDBStatusDAO implements FeedDAO{

    public DynamoDBFeedDAO() {
        super(FEED_TABLE);
    }

    @Override
    public Pair<List<Status>, Boolean> getFeed(String alias, int pageSize, Status lastStatus) {
        return doQuery(alias, pageSize, lastStatus);
    }

    @Override
    public void postToFollowers(Status status, List<String> followers) {
        if (followers.size() > 0) {
            doWrite(status, followers);
        }
    }

}
