package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;

public class FeedPresenter extends PagedPresenter<Status> {

    public FeedPresenter(PagedView<Status> view) {
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<Status> observer) {
        GetFeedRequest request = new GetFeedRequest(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        getStatusService().loadMoreFeed(request, observer);
    }

}
