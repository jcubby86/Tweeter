package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

public class StoryPresenter extends PagedPresenter<Status> {

    public StoryPresenter(PagedView<Status> view) {
        super(view);
    }

    @Override
    protected void callService(User user) {
        GetStoryRequest request = new GetStoryRequest(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        getStatusService().loadMoreStory(request, new BackgroundTaskObserver<GetStoryResponse>() {
            @Override
            public void handleFailure(String message) {
                loading = false;
                view.removeLoadingFooter();
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(GetStoryResponse response) {
                List<Status> items = response.getItems();

                hasMorePages = response.isHasMorePages();
                lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
                loading = false;
                view.removeLoadingFooter();
                view.displayMoreItems(items);
            }
        });
    }

}
