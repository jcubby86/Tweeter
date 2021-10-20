package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;


public class FollowersPresenter extends PagedPresenter<User, GetFollowersResponse> {

    public FollowersPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    protected void callService(User user, BackgroundTaskObserver<GetFollowersResponse> observer) {
        GetFollowersRequest request = new GetFollowersRequest(Cache.getInstance().getCurrUserAuthToken(), user.getAlias(), PAGE_SIZE, lastItem);
        getFollowService().loadMoreFollowers(request, observer);
    }

}
