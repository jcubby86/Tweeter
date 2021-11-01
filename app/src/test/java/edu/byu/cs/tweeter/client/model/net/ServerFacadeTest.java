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
            assertNull(response.getMessage());

            assertEquals("@allen", response.getUser().getAlias());
            assertNotNull(response.getAuthToken());
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
            assertNull(response.getMessage());

            //9 because the FakeData skips the first user, @allen
            assertEquals(9, response.getItems().size());
            assertEquals("@amy", response.getItems().get(0).getAlias());
            assertEquals("@elizabeth", response.getItems().get(response.getItems().size()-1).getAlias());
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
            assertNull(response.getMessage());

            assertEquals(20, response.getFollowersCount());
            assertEquals(20, response.getFollowingCount());
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}