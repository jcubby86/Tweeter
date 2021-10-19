package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public abstract class PagedPresenter<T> extends Presenter<PagedView<T>> {

    protected static final int PAGE_SIZE = 10;

    protected T lastItem;
    protected boolean hasMorePages = true;
    protected boolean loading = false;

    public PagedPresenter(PagedView<T> view) {
        super(view);
    }

    public void loadMoreItems(User user) {
        if (!loading && hasMorePages) {
            loading = true;
            view.addLoadingFooter();

            callService(user);
        }
    }

    protected abstract void callService(User user);

    public boolean isLoading() {
        return loading;
    }

    public void getUser(String userAlias) {
        view.displayInfoMessage("Getting user's profile...");

        GetUserRequest request = new GetUserRequest(Cache.getInstance().getCurrUserAuthToken(), userAlias);
        getUserService().getUser(request, new BackgroundTaskObserver<GetUserResponse>() {

            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(GetUserResponse response) {
                view.clearInfoMessage();
                view.navigateToUser(response.getUser());
            }
        });
    }
}
