package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDBDAOFactory;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.service.UserService;

public abstract class Handler<REQUEST extends Request, RESPONSE extends Response> implements RequestHandler<REQUEST, RESPONSE> {

    protected UserService getUserService(LambdaLogger logger) {
        return new UserService(new DynamoDBDAOFactory(logger), logger);
    }

    protected FollowService getFollowService(LambdaLogger logger) {
        return new FollowService(new DynamoDBDAOFactory(logger), logger);
    }

    protected StatusService getStatusService(LambdaLogger logger) {
        return new StatusService(new DynamoDBDAOFactory(logger), logger);
    }
}
