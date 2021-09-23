package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter{

    private static final int PAGE_SIZE = 10;

    private final FollowService followService;
    private final UserService userService;
    private final FollowingView view;

    private User lastFollowee;
    private boolean hasMorePages = true;
    private boolean loading = false;

    public interface FollowingView{
        void displayErrorMessage(String message);
        void displayMoreFollowing(List<User> followees);
        void navigateToUser(User user);

        void displayInfoMessage(String message);
        void clearInfoMessage();

        void removeLoadingFooter();
        void addLoadingFooter();
    }

    public FollowingPresenter(FollowingView view){
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public boolean isLoading() {
        return loading;
    }

    public void loadMoreFollowing(User user) {
        if (!loading && hasMorePages) {
            loading = true;
            view.addLoadingFooter();

            followService.loadMoreFollowing(user, PAGE_SIZE, lastFollowee, new FollowService.FollowingObserver() {
                @Override
                public void handleSuccess(List<User> followees, boolean hasMorePages) {
                    FollowingPresenter.this.hasMorePages = hasMorePages;
                    FollowingPresenter.this.lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
                    loading = false;
                    view.removeLoadingFooter();
                    view.displayMoreFollowing(followees);
                }
                @Override
                public void handleFailure(String message) {
                    loading = false;
                    view.removeLoadingFooter();
                    view.displayErrorMessage(message);
                }
            });
        }
    }

    public void getUser(String userAlias) {
        view.displayInfoMessage("Getting user's profile...");

        userService.getUser(userAlias, new UserService.GetUserObserver(){
            @Override
            public void handleSuccess(User user) {
                view.clearInfoMessage();
                view.navigateToUser(user);
            }
            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayErrorMessage(message);
            }
        });

    }

}
