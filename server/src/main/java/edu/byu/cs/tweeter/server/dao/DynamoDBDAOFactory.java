package edu.byu.cs.tweeter.server.dao;

public class DynamoDBDAOFactory implements DAOFactory {

    private static DynamoDBDAOFactory instance = new DynamoDBDAOFactory();

    public static DynamoDBDAOFactory getInstance(){
        return instance;
    }

    public static void setInstance(DynamoDBDAOFactory dynamoDBDAOFactory) {
        DynamoDBDAOFactory.instance = dynamoDBDAOFactory;
    }

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
