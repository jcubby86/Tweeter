package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {

    public StoryPresenter(PagedView<Status> view) {
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<Status> observer) {
        getStatusService().loadMoreStory(user, PAGE_SIZE, lastItem, observer);
    }

}
