package edu.byu.cs.tweeter.client.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

public class StatusServiceTest {

    private interface GetStoryObserver extends BackgroundTaskObserver<GetStoryResponse> {}

    StatusService statusServiceSpy;

    AuthToken authToken;

    @Before
    public void setUp() throws IOException, TweeterRemoteException {
        statusServiceSpy = Mockito.spy(new StatusService());

        LoginRequest request = new LoginRequest("@allen","12345");
        LoginResponse response = new ServerFacade().login(request);
        authToken = response.getAuthToken();
    }

    @Test
    public void getStory() throws InterruptedException {
        GetStoryObserver mockObserver = Mockito.mock(GetStoryObserver.class);
        CountDownLatch latch = new CountDownLatch(1);

        Answer<Void> a = invocation -> {
            latch.countDown();
            return null;
        };
        Mockito.doAnswer(a).when(mockObserver).handleSuccess(Mockito.any());
        Mockito.doAnswer(a).when(mockObserver).handleFailure(Mockito.any());

        BackgroundTaskHandler<GetStoryResponse> handler = new BackgroundTaskHandler<>(Looper.getMainLooper(), mockObserver);

        Mockito.doReturn(handler).when(statusServiceSpy).getHandler(Mockito.any());

        GetStoryRequest request = new GetStoryRequest(authToken, "@allen", 10 , null);

        statusServiceSpy.getStory(request, mockObserver);

        latch.await();

        ArgumentCaptor<GetStoryResponse> captor = ArgumentCaptor.forClass(GetStoryResponse.class);
        Mockito.verify(mockObserver).handleSuccess(captor.capture());

        GetStoryResponse response = captor.getValue();

        assertTrue(response.isSuccess());
        assertNull(response.getMessage());

        assertNotNull(response.getItems());
        assertTrue(response.isHasMorePages());
    }
}