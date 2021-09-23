package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {

    private static final int PAGE_SIZE = 10;

    private final StatusService statusService;
    private final UserService userService;
    private final FeedView view;

    private Status lastStatus;
    private boolean hasMorePages = true;
    private boolean loading = false;

    public interface FeedView{
        void displayErrorMessage(String message);
        void displayMoreStatuses(List<Status> statuses);
        void navigateToUser(User user);

        void displayInfoMessage(String message);
        void clearInfoMessage();

        void removeLoadingFooter();
        void addLoadingFooter();
    }

    public FeedPresenter(FeedView view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public boolean isLoading() {
        return loading;
    }

    public void loadMoreOfFeed(User user){
        if (!loading && hasMorePages) {
            loading = true;
            view.addLoadingFooter();

            statusService.loadMoreOfFeed(user, PAGE_SIZE, lastStatus, new StatusService.GetFeedObserver() {
                @Override
                public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
                    FeedPresenter.this.hasMorePages = hasMorePages;
                    FeedPresenter.this.lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
                    loading = false;
                    view.removeLoadingFooter();
                    view.displayMoreStatuses(statuses);
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
