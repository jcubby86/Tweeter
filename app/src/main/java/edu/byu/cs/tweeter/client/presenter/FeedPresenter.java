package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status, FeedPresenter.FeedView> {

    public interface FeedView extends PagedView<Status> {}

    public FeedPresenter(FeedView view) {
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<Status> observer) {
        statusService.loadMoreFeed(user, PAGE_SIZE, lastItem, observer);
    }

}
