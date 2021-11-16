package edu.byu.cs.tweeter.server.dao;

public class DynamoDBDAOFactory implements DAOFactory {

    @Override
    public AuthDAO getAuthDAO() {
        return new DynamoDBAuthDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DynamoDBFeedDAO();
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new DynamoDBFollowDAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoDBStoryDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new DynamoDBUserDAO();
    }
}
