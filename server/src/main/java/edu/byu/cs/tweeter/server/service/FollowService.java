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
        if (isAuthorized(request)) {
            String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
            Pair<List<String>, Boolean> data = getFollowDAO().getFollowing(request.getTargetUserAlias(),
                    request.getLimit(), last);
            List<User> users = getUserDAO().getUserList(data.getFirst());
            System.out.println("Retrieved following for " + request.getTargetUserAlias());
            return new GetFollowingResponse(users, data.getSecond());
        } else {
            logUnauthorized(request);
            return new GetFollowingResponse(NOT_AUTHORIZED);
        }
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request){
        if (isAuthorized(request)) {
            String last = request.getLastItem() == null ? null : request.getLastItem().getAlias();
            Pair<List<String>, Boolean> data = getFollowDAO().getFollowers(request.getTargetUserAlias(),
                    request.getLimit(), last);
            List<User> users = getUserDAO().getUserList(data.getFirst());

            System.out.println("Retrieved followers for " + request.getTargetUserAlias());
            return new GetFollowersResponse(users, data.getSecond());
        } else {
            logUnauthorized(request);
            return new GetFollowersResponse(NOT_AUTHORIZED);
        }
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        if (isAuthorized(request)) {
            System.out.println("Checking if " + request.getAuthToken().getUserAlias() + "is following " + request.getFollowee());
            return new IsFollowerResponse(getFollowDAO().isFollower(request));
        } else {
            logUnauthorized(request);
            return new IsFollowerResponse(NOT_AUTHORIZED);
        }
    }

    public FollowResponse follow(FollowRequest request) {
        if (isAuthorized(request)) {
            getFollowDAO().follow(request);
            System.out.println(request.getAuthToken().getUserAlias() + " following " + request.getFolloweeAlias());
            return new FollowResponse();
        } else {
            logUnauthorized(request);
            return new FollowResponse(NOT_AUTHORIZED);
        }
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        if (isAuthorized(request)) {
            getFollowDAO().unfollow(request);
            System.out.println(request.getAuthToken().getUserAlias() + " unfollowing " + request.getFolloweeAlias());
            return new UnfollowResponse();
        } else {
            logUnauthorized(request);
            return new UnfollowResponse(NOT_AUTHORIZED);
        }
    }

    public GetCountResponse getCount(GetCountRequest request) {
        if (isAuthorized(request)) {
            Pair<Integer, Integer> data = getFollowDAO().getCount(request);
            System.out.println("Getting counts for " + request.getTargetUserAlias());
            return new GetCountResponse(data.getFirst(), data.getSecond());
        } else {
            logUnauthorized(request);
            return new GetCountResponse(NOT_AUTHORIZED);
        }
    }

    public List<String> sqsGetFollowers(String targetUserAlias, String lastFollowerAlias, int pageSize){
        return getFollowDAO().getFollowers(targetUserAlias,
                pageSize, lastFollowerAlias).getFirst();
    }

}
