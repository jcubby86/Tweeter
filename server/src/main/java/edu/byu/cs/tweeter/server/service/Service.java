package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.byu.cs.tweeter.model.net.request.AuthorizedRequest;
import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.util.Pair;

public abstract class Service {
    protected static final String NOT_AUTHORIZED = "User not authorized";

    protected final LambdaLogger logger;
    private final DAOFactory daoFactory;

    public Service(DAOFactory daoFactory, LambdaLogger logger){
        this.daoFactory = daoFactory;
        this.logger = logger;
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

    protected boolean isAuthorized(AuthorizedRequest request){
        request.checkRequest();
        return getAuthDAO().isAuthorized(request.getAuthToken());
    }

    protected void logUnauthorized(AuthorizedRequest request) {
        logger.log(NOT_AUTHORIZED + ": " + request.getAuthToken().getUserAlias());
    }
}
