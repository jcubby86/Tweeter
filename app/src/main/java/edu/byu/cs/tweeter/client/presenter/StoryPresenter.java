package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

public class StoryPresenter extends PagedPresenter<Status, GetStoryResponse> {

    public StoryPresenter(PagedView<Status> view) {
        super(view);
    }

    @Override
    protected void callService(User user, BackgroundTaskObserver<GetStoryResponse> observer) {
        GetStoryRequest request = new GetStoryRequest(Cache.getInstance().getCurrUserAuthToken(), user.getAlias(), PAGE_SIZE, lastItem);
        getStatusService().getStory(request, observer);
    }

}
