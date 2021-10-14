package edu.byu.cs.tweeter.client.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.presenter.observers.MainView;
import edu.byu.cs.tweeter.model.domain.Status;

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
    }

    @Test
    public void testPostStatus_postStatusSucceeds(){
        Answer<Void> successAnswer = invocation -> {
            SimpleNotificationObserver observer = invocation.getArgumentAt(1, SimpleNotificationObserver.class);
            observer.handleSuccess();
            return null;
        };

        runTest(successAnswer);

        Mockito.verify(mockView).statusPostComplete();
        Mockito.verify(mockView, Mockito.times(0)).displayErrorMessage(Mockito.anyString());
    }

    @Test
    public void testPostStatus_postStatusFails(){
        Answer<Void> failureAnswer = invocation -> {
            SimpleNotificationObserver observer = invocation.getArgumentAt(1, SimpleNotificationObserver.class);
            observer.handleFailure("Failure Message");
            return null;
        };

        runTest(failureAnswer);

        Mockito.verify(mockView).displayErrorMessage("Failure Message");
        Mockito.verify(mockView, Mockito.times(0)).statusPostComplete();
    }

    private void runTest(Answer<Void> a){
        Mockito.doAnswer(a).when(mockStatusService).postStatus(Mockito.any(Status.class), Mockito.any(SimpleNotificationObserver.class));

        mainPresenterSpy.postStatus("Test Post");

        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockCache).getCurrUser();

        ArgumentCaptor<Status> captor = ArgumentCaptor.forClass(Status.class);
        Mockito.verify(mockStatusService).postStatus(captor.capture(), Mockito.any(SimpleNotificationObserver.class));

        assertEquals("Test Post", captor.getValue().getPost());
        assertEquals(mockCache.getCurrUser(), captor.getValue().getUser());

        Mockito.verify(mockView).clearInfoMessage();
    }
}