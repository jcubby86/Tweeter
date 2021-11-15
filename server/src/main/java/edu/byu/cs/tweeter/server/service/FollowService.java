package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.util.Pair;


public class FollowService extends Service{

    public FollowService(DAOFactory daoFactory, LambdaLogger logger) {
        super(daoFactory, logger);
    }

    public GetFollowingResponse getFollowing(GetFollowingRequest request) {
        if (isAuthorized(request)) {
            String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
            List<User> users = getFollowDAO().getFollowingPaged(request.getTargetUserAlias(),
                    request.getLimit(), last);
            logger.log("Retrieved following for " + request.getTargetUserAlias());
            return new GetFollowingResponse(users, users.size() > 0);
        } else {
            logUnauthorized(request);
            return new GetFollowingResponse(NOT_AUTHORIZED);
        }
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request){
        if (isAuthorized(request)) {
            String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
            List<User> users = getFollowDAO().getFollowersPaged(request.getTargetUserAlias(),
                    request.getLimit(), last);
            logger.log("Retrieved followers for " + request.getTargetUserAlias());
            return new GetFollowersResponse(users, users.size() > 0);
        } else {
            logUnauthorized(request);
            return new GetFollowersResponse(NOT_AUTHORIZED);
        }
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        if (isAuthorized(request)) {
            logger.log("Checking if " + request.getFollower() + "is following " + request.getFollowee());
            return new IsFollowerResponse(getFollowDAO().isFollower(request.getFollower(), request.getFollowee()));
        } else {
            logUnauthorized(request);
            return new IsFollowerResponse(NOT_AUTHORIZED);
        }
    }

    public FollowResponse follow(FollowRequest request) {
        if (isAuthorized(request)) {
            getFollowDAO().putItem(request.getFollowerAlias(), request.getFolloweeAlias());
            logger.log(request.getFollowerAlias() + " following " + request.getFolloweeAlias());
            return new FollowResponse();
        } else {
            logUnauthorized(request);
            return new FollowResponse(NOT_AUTHORIZED);
        }
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        if (isAuthorized(request)) {
            getFollowDAO().deleteItem(request.getFollowerAlias(), request.getFolloweeAlias());
            logger.log(request.getFollowerAlias() + " unfollowing " + request.getFolloweeAlias());
            return new UnfollowResponse();
        } else {
            logUnauthorized(request);
            return new UnfollowResponse(NOT_AUTHORIZED);
        }
    }

    public GetCountResponse getCount(GetCountRequest request) {
        if (isAuthorized(request)) {
            Pair<Integer, Integer> data = getFollowDAO().getCount(request.getTargetUserAlias());
            logger.log("Getting counts for " + request.getTargetUserAlias());
            return new GetCountResponse(data.getFirst(), data.getSecond());
        } else {
            logUnauthorized(request);
            return new GetCountResponse(NOT_AUTHORIZED);
        }
    }

}
