package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.UserService;

public abstract class Handler<REQUEST extends Request, RESPONSE extends Response> implements RequestHandler<REQUEST, RESPONSE> {
    protected FollowService getFollowService(){
        return new FollowService();
    }
    protected UserService getUserService(){
        return new UserService();
    }
    protected StatusService getStatusService(){
        return new StatusService();
    }
}
