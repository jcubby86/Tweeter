package edu.byu.cs.tweeter.client.model.service;

import static org.junit.Assert.*;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

public class StatusServiceTest {

    private interface GetStoryObserver extends BackgroundTaskObserver<GetStoryResponse> {}

    StatusService statusServiceSpy;
    GetStoryObserver mockObserver;
    CountDownLatch latch;

    @Before
    public void setUp() {
        statusServiceSpy = Mockito.spy(new StatusService());
        mockObserver = Mockito.mock(GetStoryObserver.class);

        latch = new CountDownLatch(1);

        Answer<Void> a = invocation -> {
            latch.countDown();
            return null;
        };
        Mockito.doAnswer(a).when(mockObserver).handleSuccess(Mockito.any());

        BackgroundTaskHandler<GetStoryResponse> handler = new BackgroundTaskHandler<>(Looper.getMainLooper(), mockObserver);

        Mockito.doReturn(handler).when(statusServiceSpy).getHandler(Mockito.any());
    }

    @Test
    public void getStory() throws InterruptedException {
        GetStoryRequest request = new GetStoryRequest(new AuthToken(), "@allen", 10 , null);

        statusServiceSpy.getStory(request, mockObserver);

        latch.await();

        ArgumentCaptor<GetStoryResponse> captor = ArgumentCaptor.forClass(GetStoryResponse.class);
        Mockito.verify(mockObserver).handleSuccess(captor.capture());

        GetStoryResponse response = captor.getValue();

        assertTrue(response.isSuccess());
        assertNull(response.getMessage());

        assertTrue(response.isHasMorePages());
        assertEquals(10, response.getItems().size());
        assertEquals("@allen", response.getItems().get(0).getUser().getAlias());
        assertEquals("@elizabeth", response.getItems().get(response.getItems().size()-1).getUser().getAlias());
    }
}