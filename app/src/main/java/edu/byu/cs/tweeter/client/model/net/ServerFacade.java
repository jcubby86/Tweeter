package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;
import java.util.Map;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://89ttjrxeeg.execute-api.us-west-2.amazonaws.com/dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    private <REQUEST extends Request, RESPONSE extends Response> RESPONSE post(String urlPath, REQUEST request, Map<String, String> headers, Class<RESPONSE> returnType) throws IOException, TweeterRemoteException {
        RESPONSE response = clientCommunicator.doPost(urlPath, request, headers, returnType);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
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
    public GetFollowingResponse getFollowees(GetFollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        return post(urlPath, request, null, GetFollowingResponse.class);
    }

    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {
        return post("/follow", request, null, FollowResponse.class);
    }
}