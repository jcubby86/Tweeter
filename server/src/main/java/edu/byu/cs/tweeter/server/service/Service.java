package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.byu.cs.tweeter.server.dao.DataAccessException;
import edu.byu.cs.tweeter.server.dao.DynamoDBFollowDao;
import edu.byu.cs.tweeter.server.dao.FollowDao;
import edu.byu.cs.tweeter.server.util.FakeData;

public abstract class Service {
    protected final String INTERNAL_SERVER_ERROR = "[InternalServerError]";
    protected FakeData getFakeData() {
        return new FakeData();
    }
    protected FollowDao getFollowDao() {
        return new DynamoDBFollowDao();
    }

}
