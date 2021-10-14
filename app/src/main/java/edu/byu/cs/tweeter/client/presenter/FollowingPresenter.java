package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;


public class FollowingPresenter extends PagedPresenter<User> {

    public FollowingPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<User> observer) {
        GetFollowingRequest request = new GetFollowingRequest(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        getFollowService().loadMoreFollowing(request, observer);
    }

}
