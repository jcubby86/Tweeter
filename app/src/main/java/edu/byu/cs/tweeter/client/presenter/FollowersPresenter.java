package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;


public class FollowersPresenter extends PagedPresenter<User> {

    public FollowersPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    protected void callService(User user) {
        GetFollowersRequest request = new GetFollowersRequest(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        getFollowService().loadMoreFollowers(request, new BackgroundTaskObserver<GetFollowersResponse>() {
            @Override
            public void handleFailure(String message) {
                loading = false;
                view.removeLoadingFooter();
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(GetFollowersResponse response) {
                List<User> items = response.getItems();

                hasMorePages = response.isHasMorePages();
                lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
                loading = false;
                view.removeLoadingFooter();
                view.displayMoreItems(items);
            }
        });
    }

}
