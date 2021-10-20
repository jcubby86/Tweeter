package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;


public class FollowingPresenter extends PagedPresenter<User, GetFollowingResponse> {

    public FollowingPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    protected void callService(User user, BackgroundTaskObserver<GetFollowingResponse> observer) {
        GetFollowingRequest request = new GetFollowingRequest(Cache.getInstance().getCurrUserAuthToken(), user.getAlias(), PAGE_SIZE, lastItem);
        getFollowService().getFollowing(request, observer);
    }

}
