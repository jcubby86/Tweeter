package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.service.UserService;

public abstract class Handler<REQUEST extends Request, RESPONSE extends Response> implements RequestHandler<REQUEST, RESPONSE> {

    private UserService userService;
    private FollowService followService;
    private StatusService statusService;

    protected UserService getUserService() {
        if (userService == null){
            userService = new UserService();
        }
        return userService;
    }

    protected FollowService getFollowService() {
        if (followService == null){
            followService = new FollowService();
        }
        return followService;
    }

    protected StatusService getStatusService() {
        if (statusService == null){
            statusService = new StatusService();
        }
        return statusService;
    }
}
