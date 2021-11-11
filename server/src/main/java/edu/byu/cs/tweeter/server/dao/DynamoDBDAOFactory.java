package edu.byu.cs.tweeter.server.dao;

public class DynamoDBDAOFactory implements DAOFactory {

    @Override
    public AuthDAO getAuthDAO() {
        return new DynamoDBAuthDAO(this);
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DynamoDBFeedDAO(this);
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new DynamoDBFollowDAO(this);
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoDBStoryDAO(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return new DynamoDBUserDAO(this);
    }
}
