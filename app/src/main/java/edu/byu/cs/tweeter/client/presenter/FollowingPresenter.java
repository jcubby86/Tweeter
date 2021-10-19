package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;


public class FollowingPresenter extends PagedPresenter<User> {

    public FollowingPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    protected void callService(User user) {
        GetFollowingRequest request = new GetFollowingRequest(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        getFollowService().loadMoreFollowing(request, new BackgroundTaskObserver<GetFollowingResponse>() {
            @Override
            public void handleFailure(String message) {
                loading = false;
                view.removeLoadingFooter();
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(GetFollowingResponse response) {
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
