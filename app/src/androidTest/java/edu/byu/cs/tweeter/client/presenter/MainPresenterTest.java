package edu.byu.cs.tweeter.client.presenter;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.client.presenter.observers.MainView;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterTest {

    private interface StoryView extends PagedView<Status>{}

    private MainView mockMainView;
    private StoryView mockStoryView;

    private LoginPresenter loginPresenter;
    private MainPresenter mainPresenter;
    private StoryPresenter storyPresenter;

    private CountDownLatch loginLatch;
    private CountDownLatch postStatusLatch;
    private CountDownLatch storyLatch;

    private final User testUser = new User("Jacob", "Bastian", "@muffinjr", "");
    private final Status testStatus = new Status(AuthToken.generateUniqueID(), testUser, "", new ArrayList<>(), new ArrayList<>());

    @Before
    public void setUp() {
        AuthenticateView mockAuthenticateView = Mockito.mock(AuthenticateView.class);
        loginPresenter = new LoginPresenter(mockAuthenticateView);
        loginLatch = new CountDownLatch(1);
        Answer<Void> loginAnswer = invocation -> {
            loginLatch.countDown();
            return null;
        };
        Mockito.doAnswer(loginAnswer).when(mockAuthenticateView).displayErrorMessage(Mockito.any());
        Mockito.doAnswer(loginAnswer).when(mockAuthenticateView).authenticateUser(Mockito.any());


        mockMainView = Mockito.mock(MainView.class);
        mainPresenter = new MainPresenter(mockMainView);
        postStatusLatch = new CountDownLatch(1);
        Answer<Void> postStatusAnswer = invocation -> {
            postStatusLatch.countDown();
            return null;
        };
        Mockito.doAnswer(postStatusAnswer).when(mockMainView).clearInfoMessage();


        mockStoryView = Mockito.mock(StoryView.class);
        storyPresenter = new StoryPresenter(mockStoryView);
        storyLatch = new CountDownLatch(1);
        Answer<Void> storyAnswer = invocation -> {
            storyLatch.countDown();
            return null;
        };
        Mockito.doAnswer(storyAnswer).when(mockStoryView).removeLoadingFooter();

    }

    @Test
    public void testPostStatus() throws Throwable {
        runOnUiThread(() -> loginPresenter.login("@muffinjr", "12345"));
        loginLatch.await();


        runOnUiThread(() -> mainPresenter.postStatus(testStatus.getPost()));
        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        postStatusLatch.await();

        Mockito.verify(mockMainView).clearInfoMessage();
        Mockito.verify(mockMainView).statusPostComplete();
        Mockito.verify(mockMainView, Mockito.times(0)).displayErrorMessage(Mockito.anyString());


        runOnUiThread(() -> storyPresenter.loadMoreItems(Cache.getInstance().getCurrUser()));
        storyLatch.await();

        ArgumentCaptor<List<Status>> captor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(mockStoryView).displayMoreItems(captor.capture());
        Status lastStatus = captor.getValue().get(0);

        assertEquals(testStatus.getPost(), lastStatus.getPost());
        assertEquals(testStatus.getAuthor(), lastStatus.getAuthor());
        assertEquals(testStatus.getMentions(), lastStatus.getMentions());
        assertEquals(testStatus.getUrls(), lastStatus.getUrls());
        assertEquals(testStatus.getUser(), lastStatus.getUser());
    }
}