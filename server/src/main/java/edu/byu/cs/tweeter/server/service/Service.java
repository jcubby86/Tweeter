package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public abstract class Service {
    public static final String INTERNAL_SERVER_ERROR = "[InternalServerError]";
    private final DAOFactory daoFactory;

    public Service(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    protected FakeData getFakeData() {
        return new FakeData();
    }
    protected FollowDAO getFollowDAO() {
        return daoFactory.getFollowDAO();
    }
    protected UserDAO getUserDAO(){
        return daoFactory.getUserDAO();
    }
    protected AuthDAO getAuthDAO(){
        return daoFactory.getAuthDAO();
    }
    protected StoryDAO getStoryDAO(){
        return daoFactory.getStoryDAO();
    }
    protected FeedDAO getFeedDAO(){
        return daoFactory.getFeedDAO();
    }

}
