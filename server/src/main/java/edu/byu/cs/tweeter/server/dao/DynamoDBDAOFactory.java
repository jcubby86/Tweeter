package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class DynamoDBDAOFactory implements DAOFactory {

    private final LambdaLogger logger;

    public DynamoDBDAOFactory(LambdaLogger logger) {
        this.logger = logger;
    }

    @Override
    public AuthDAO getAuthDAO() {
        return new DynamoDBAuthDAO(this, logger);
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DynamoDBFeedDAO(this, logger);
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new DynamoDBFollowDAO(this, logger);
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoDBStoryDAO(this, logger);
    }

    @Override
    public UserDAO getUserDAO() {
        return new DynamoDBUserDAO(this, logger);
    }
}
