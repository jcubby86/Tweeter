package edu.byu.cs.tweeter.client.model.net;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

class ServerFacadeTest {

    private ServerFacade serverFacade;
    private AuthToken authToken;

    @BeforeEach
    void setUp() throws IOException, TweeterRemoteException {
        serverFacade = new ServerFacade();
        LoginRequest request = new LoginRequest("@username","password");
        LoginResponse response = serverFacade.login(request);
        authToken = response.getAuthToken();
    }

    @Test
    void testRegister(){
        String username = "@" + AuthToken.generateUniqueID();
        RegisterRequest request = new RegisterRequest(username, "password","first", "last", "");
        try {
            RegisterResponse response = serverFacade.register(request);

            assertTrue(response.isSuccess());
            assertNull(response.getMessage());

            assertEquals(username, response.getUser().getAlias());
            assertNotNull(response.getAuthToken());
            assertNotNull(response.getAuthToken().getToken());
            assertNotNull(response.getAuthToken().getTimeMillis());
            assertEquals(username, response.getAuthToken().getUserAlias());
        } catch (IOException | TweeterRemoteException e) {
            fail(e);
        }
    }

    @Test
    void testGetFollowers(){
        GetFollowersRequest request = new GetFollowersRequest(authToken, "@allen", 10, null);
        try {
            GetFollowersResponse response = serverFacade.getFollowers(request);

            assertTrue(response.isSuccess());
            assertNull(response.getMessage());

            assertNotNull(response.getItems());
        } catch (IOException | TweeterRemoteException e) {
            fail(e);
        }
    }

    @Test
    void testGetCounts(){
        GetCountRequest request = new GetCountRequest(authToken, "@allen");
        try {
            GetCountResponse response = serverFacade.getCount(request);

            assertTrue(response.isSuccess());
            assertNull(response.getMessage());
        } catch (IOException | TweeterRemoteException e) {
            fail(e);
        }
    }

}