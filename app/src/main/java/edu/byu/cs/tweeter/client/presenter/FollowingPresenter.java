package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;


public class FollowingPresenter extends PagedPresenter<User,FollowingPresenter.FollowingView> {

    public interface FollowingView extends PagedView<User> {}

    public FollowingPresenter(FollowingView view){
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<User> observer) {
        followService.loadMoreFollowing(user, PAGE_SIZE, lastItem, (FollowService.GetFollowingObserver) observer);
    }

}
