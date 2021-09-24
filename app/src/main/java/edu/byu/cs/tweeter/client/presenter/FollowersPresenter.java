package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;


public class FollowersPresenter extends PagedPresenter<User, FollowersPresenter.FollowersView> {

    public interface FollowersView extends PagedView<User> {}

    public FollowersPresenter(FollowersView view){
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<User> observer) {
        followService.loadMoreFollowers(user, PAGE_SIZE, lastItem, (FollowService.GetFollowersObserver) observer);
    }

}
