package edu.byu.cs.tweeter.client.model.net;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

class ServerFacadeTest {

    private ServerFacade serverFacade;

    @BeforeEach
    void setUp() {
        serverFacade = new ServerFacade();
    }

    @Test
    void testRegister(){
        RegisterRequest request = new RegisterRequest("@username", "password","first", "last", "");
        try {
            RegisterResponse response = serverFacade.register(request);
            assertTrue(response.isSuccess());
            assertNotNull(response.getUser());
            assertNotNull(response.getAuthToken());
            assertNull(response.getMessage());
        } catch (IOException | TweeterRemoteException e) {
            fail(e);
        }
    }

    @Test
    void testGetFollowers(){
        GetFollowersRequest request = new GetFollowersRequest(new AuthToken(), "@allen", 10, null);
        try {
            GetFollowersResponse response = serverFacade.getFollowers(request);
            assertTrue(response.isSuccess());
            assertNotNull(response.getItems());
            assertNull(response.getMessage());
        } catch (IOException | TweeterRemoteException e) {
            fail(e);
        }
    }

    @Test
    void testGetCounts(){
        GetCountRequest request = new GetCountRequest(new AuthToken(), "@allen");
        try {
            GetCountResponse response = serverFacade.getCount(request);
            assertTrue(response.isSuccess());
            assertEquals(20, response.getFollowersCount());
            assertEquals(20, response.getFollowingCount());
            assertNull(response.getMessage());
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}