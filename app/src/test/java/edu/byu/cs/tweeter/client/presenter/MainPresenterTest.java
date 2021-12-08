package edu.byu.cs.tweeter.client.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.observers.MainView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

class MainPresenterTest {

    private MainView mockView;
    private StatusService mockStatusService;
    private Cache mockCache;

    private MainPresenter mainPresenterSpy;

    @BeforeEach
    void setUp() {
        mockView = Mockito.mock(MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.doReturn(mockStatusService).when(mainPresenterSpy).getStatusService();

        Cache.setInstance(mockCache);
        Mockito.doReturn(new User("@allen", "", "")).when(mockCache).getCurrUser();
    }

    @Test
    public void testPostStatus_postStatusSucceeds(){
        Answer<Void> successAnswer = invocation -> {
            @SuppressWarnings("unchecked")
            BackgroundTaskObserver<PostStatusResponse> observer = invocation.getArgument(1, BackgroundTaskObserver.class);
            observer.handleSuccess(new PostStatusResponse());
            return null;
        };

        runTest(successAnswer);

        Mockito.verify(mockView).statusPostComplete();
        Mockito.verify(mockView, Mockito.times(0)).displayErrorMessage(Mockito.anyString());
    }

    @Test
    public void testPostStatus_postStatusFails(){
        Answer<Void> failureAnswer = invocation -> {
            @SuppressWarnings("unchecked")
            BackgroundTaskObserver<PostStatusResponse> observer = invocation.getArgument(1, BackgroundTaskObserver.class);
            observer.handleFailure("Failure Message");
            return null;
        };

        runTest(failureAnswer);

        Mockito.verify(mockView).displayErrorMessage("Failure Message");
        Mockito.verify(mockView, Mockito.times(0)).statusPostComplete();
    }

    private void runTest(Answer<Void> a){
        Mockito.doAnswer(a).when(mockStatusService).postStatus(Mockito.any(PostStatusRequest.class), Mockito.any());

        mainPresenterSpy.postStatus("Test Post");

        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockCache).getCurrUser();

        ArgumentCaptor<PostStatusRequest> captor = ArgumentCaptor.forClass(PostStatusRequest.class);
        Mockito.verify(mockStatusService).postStatus(captor.capture(), Mockito.any());

        assertEquals("Test Post", captor.getValue().getStatus().getPost());
        assertEquals(mockCache.getCurrUser().getAlias(), captor.getValue().getStatus().getAuthor());

        Mockito.verify(mockView).clearInfoMessage();
    }
}