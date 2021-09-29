package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;


public class FollowingPresenter extends PagedPresenter<User> {

    public FollowingPresenter(PagedView<User> view){
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<User> observer) {
        followService.loadMoreFollowing(user, PAGE_SIZE, lastItem, observer);
    }

}
