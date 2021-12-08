package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
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

    private <REQUEST extends Request, RESPONSE extends Response> RESPONSE post(String urlPath, REQUEST request, Class<RESPONSE> returnType) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, returnType);
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        return post("/login", request, LoginResponse.class);
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        return post("/register", request, RegisterResponse.class);
    }

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        return post("/logout", request, LogoutResponse.class);
    }

    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        return post("/getuser", request, GetUserResponse.class);
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
        return post("/getfollowing", request, GetFollowingResponse.class);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException {
        return post("/getfollowers", request, GetFollowersResponse.class);
    }

    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {
        return post("/follow", request, FollowResponse.class);
    }

    public UnfollowResponse unfollow(UnfollowRequest request) throws IOException, TweeterRemoteException {
        return post("/unfollow", request, UnfollowResponse.class);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) throws IOException, TweeterRemoteException {
        return post("/isfollower", request, IsFollowerResponse.class);
    }

    public GetCountResponse getCount(GetCountRequest request) throws IOException, TweeterRemoteException {
        return post("/getcount", request, GetCountResponse.class);
    }


    public GetStoryResponse getStory(GetStoryRequest request) throws IOException, TweeterRemoteException {
        return post("/getstory", request, GetStoryResponse.class);
    }

    public GetFeedResponse getFeed(GetFeedRequest request) throws IOException, TweeterRemoteException {
        return post("/getfeed", request, GetFeedResponse.class);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        System.out.println("In ServerFacade.postStatus");
        return post("/poststatus", request, PostStatusResponse.class);
    }


    public static void main(String[] args) throws IOException, TweeterRemoteException {
        AuthToken authToken = new AuthToken("", 0L, "");
        ServerFacade serverFacade = new ServerFacade();

        serverFacade.register(new RegisterRequest("@allen","", "", "", ""));
        serverFacade.login(new LoginRequest("@allen", ""));
        serverFacade.follow(new FollowRequest(authToken, ""));
        serverFacade.getCount(new GetCountRequest(authToken, ""));
        serverFacade.getFeed(new GetFeedRequest(authToken, "", 0, null));
        serverFacade.getFollowers(new GetFollowersRequest(authToken, "", 0, null));
        serverFacade.getFollowing(new GetFollowingRequest(authToken, "", 0, null));
        serverFacade.getStory(new GetStoryRequest(authToken, "", 0, null));
        serverFacade.getUser(new GetUserRequest(authToken, ""));
        serverFacade.isFollower(new IsFollowerRequest(authToken, ""));
        serverFacade.logout(new LogoutRequest(authToken));
        serverFacade.postStatus(new PostStatusRequest(authToken, null));
        serverFacade.unfollow(new UnfollowRequest(authToken, ""));
    }
}