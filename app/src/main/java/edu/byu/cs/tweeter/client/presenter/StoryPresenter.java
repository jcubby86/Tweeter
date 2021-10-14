package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;

public class StoryPresenter extends PagedPresenter<Status> {

    public StoryPresenter(PagedView<Status> view) {
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<Status> observer) {
        GetStoryRequest request = new GetStoryRequest(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        getStatusService().loadMoreStory(request, observer);
    }

}
