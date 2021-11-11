package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;
import java.util.Map;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private static final String SERVER_URL = "https://89ttjrxeeg.execute-api.us-west-2.amazonaws.com/dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    private <REQUEST extends Request, RESPONSE extends Response> RESPONSE post(String urlPath, REQUEST request, Map<String, String> headers, Class<RESPONSE> returnType) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, headers, returnType);
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        return post("/login", request, null, LoginResponse.class);
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        return post("/register", request, null, RegisterResponse.class);
    }

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        return post("/logout", request, null, LogoutResponse.class);
    }

    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        return post("/getuser", request, null, GetUserResponse.class);
    }


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException {
        return post("/getfollowing", request, null, GetFollowingResponse.class);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException {
        return post("/getfollowers", request, null, GetFollowersResponse.class);
    }

    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {
        return post("/follow", request, null, FollowResponse.class);
    }

    public UnfollowResponse unfollow(UnfollowRequest request) throws IOException, TweeterRemoteException {
        return post("/unfollow", request, null, UnfollowResponse.class);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) throws IOException, TweeterRemoteException {
        return post("/isfollower", request, null, IsFollowerResponse.class);
    }

    public GetCountResponse getCount(GetCountRequest request) throws IOException, TweeterRemoteException {
        return post("/getcount", request, null, GetCountResponse.class);
    }


    public GetStoryResponse getStory(GetStoryRequest request) throws IOException, TweeterRemoteException {
        return post("/getstory", request, null, GetStoryResponse.class);
    }

    public GetFeedResponse getFeed(GetFeedRequest request) throws IOException, TweeterRemoteException {
        return post("/getfeed", request, null, GetFeedResponse.class);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        return post("/poststatus", request, null, PostStatusResponse.class);
    }
}