package edu.byu.cs.tweeter.server.dao;


public interface DAOFactory {
    AuthDAO getAuthDAO();
    FeedDAO getFeedDAO();
    FollowDAO getFollowDAO();
    StoryDAO getStoryDAO();
    UserDAO getUserDAO();
}
