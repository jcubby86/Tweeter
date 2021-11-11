package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public abstract class Service {
    private final DAOFactory daoFactory;

    public Service(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    protected FollowDAO getFollowDAO() {
        return daoFactory.getFollowDAO();
    }
    protected StoryDAO getStoryDAO(){
        return daoFactory.getStoryDAO();
    }
    protected UserDAO getUserDAO(){
        return daoFactory.getUserDAO();
    }
    protected AuthDAO getAuthDAO(){
        return daoFactory.getAuthDAO();
    }
    protected FeedDAO getFeedDAO(){
        return daoFactory.getFeedDAO();
    }

}
