package edu.byu.cs.tweeter.client.model.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

class StatusServiceTest {

    private interface GetStoryObserver extends BackgroundTaskObserver<GetStoryResponse>{}
    private static class GetStoryHandler extends BackgroundTaskHandler<GetStoryResponse>{
        public GetStoryHandler(BackgroundTaskObserver<GetStoryResponse> observer) {
            super(observer);
        }
    }

    StatusService statusServiceSpy;
    GetStoryHandler mockHandler;
    GetStoryObserver mockObserver;

    @BeforeEach
    void setUp() {
        statusServiceSpy = Mockito.spy(new StatusService());
        mockHandler = Mockito.mock(GetStoryHandler.class);
        mockObserver = Mockito.mock(GetStoryObserver.class);

        Mockito.doReturn(mockHandler).when(statusServiceSpy).getHandler(Mockito.any());

        Answer<Void> sendMessageAnswer = invocation -> {
            GetStoryResponse response = invocation.getArgumentAt(0, GetStoryResponse.class);
            mockObserver.handleSuccess(response);
            return null;
        };

        Answer<Void> runTaskAnswer = invocation -> {
            GetStoryTask taskSpy = Mockito.spy(invocation.getArgumentAt(0, GetStoryTask.class));
            Mockito.doAnswer(sendMessageAnswer).when(taskSpy).sendMessage(Mockito.any(GetStoryResponse.class));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(taskSpy);
            return null;
        };

        Mockito.doAnswer(runTaskAnswer).when(statusServiceSpy).runTask(Mockito.any(GetStoryTask.class));
    }

    @Test
    void testGetStory() throws InterruptedException {

        GetStoryRequest request = new GetStoryRequest(new AuthToken(), "@allen", 10 , null);

        statusServiceSpy.getStory(request, mockObserver);

        Thread.sleep(5000);

        ArgumentCaptor<GetStoryResponse> captor = ArgumentCaptor.forClass(GetStoryResponse.class);
        Mockito.verify(mockObserver).handleSuccess(captor.capture());

        GetStoryResponse response = captor.getValue();

        assertTrue(response.isSuccess());
        assertNotNull(response.getItems());
        assertNull(response.getMessage());
    }

}