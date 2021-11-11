package edu.byu.cs.tweeter.server.service;

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

    public FollowService(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public GetFollowingResponse getFollowing(GetFollowingRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetFollowingResponse("User not authorized");
        String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
        List<User> users = getFollowDAO().getFollowingPaged(request.getTargetUserAlias(),
                request.getLimit(), last);

        return new GetFollowingResponse(users, users.size() > 0);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request){
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetFollowersResponse("User not authorized");
        String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
        List<User> users = getFollowDAO().getFollowersPaged(request.getTargetUserAlias(),
                request.getLimit(), last);

        return new GetFollowersResponse(users, users.size() > 0);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new IsFollowerResponse("User not authorized");
        return new IsFollowerResponse(getFollowDAO().isFollower(request.getFollower(), request.getFollowee()));
    }

    public FollowResponse follow(FollowRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new FollowResponse("User not authorized");
        getFollowDAO().putItem(request.getFollowerAlias(), request.getFolloweeAlias());
        return new FollowResponse();
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new UnfollowResponse("User not authorized");
        getFollowDAO().deleteItem(request.getFollowerAlias(), request.getFolloweeAlias());
        return new UnfollowResponse();
    }

    public GetCountResponse getCount(GetCountRequest request) {
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetCountResponse("User not authorized");
        Pair<Integer, Integer> data = getFollowDAO().getCount(request.getTargetUserAlias());
        return new GetCountResponse(data.getFirst(), data.getSecond());
    }

}
