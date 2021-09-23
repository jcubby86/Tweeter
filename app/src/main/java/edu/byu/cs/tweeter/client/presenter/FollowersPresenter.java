package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter {

    private static final int PAGE_SIZE = 10;

    private final FollowService followService;
    private final UserService userService;
    private final FollowersView view;

    private User lastFollower;
    private boolean hasMorePages = true;
    private boolean loading = false;

    public interface FollowersView{
        void displayErrorMessage(String message);
        void displayMoreFollowers(List<User> followers);
        void navigateToUser(User user);

        void displayInfoMessage(String s);
        void clearInfoMessage();

        void removeLoadingFooter();
        void addLoadingFooter();
    }

    public FollowersPresenter(FollowersView view){
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public boolean isLoading() {
        return loading;
    }

    public void loadMoreFollowers(User user) {
        if (!loading && hasMorePages){
            loading = true;
            view.addLoadingFooter();

            followService.loadMoreFollowers(user, PAGE_SIZE, lastFollower, new FollowService.GetFollowersObserver(){

                @Override
                public void handleSuccess(List<User> followers, boolean hasMorePages) {
                    FollowersPresenter.this.hasMorePages = hasMorePages;
                    FollowersPresenter.this.lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
                    loading = false;
                    view.removeLoadingFooter();
                    view.displayMoreFollowers(followers);
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
