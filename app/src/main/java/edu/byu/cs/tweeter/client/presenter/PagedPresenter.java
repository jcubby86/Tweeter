package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

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

            callService(user, new PagedTaskObserver<T>() {
                @Override
                public void handleSuccess(List<T> items, boolean hasMorePages) {
                    PagedPresenter.this.hasMorePages = hasMorePages;
                    lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
                    loading = false;
                    view.removeLoadingFooter();
                    view.displayMoreItems(items);
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

    protected abstract void callService(User user, PagedTaskObserver<T> observer);

    public boolean isLoading() {
        return loading;
    }

    public void getUser(String userAlias) {
        view.displayInfoMessage("Getting user's profile...");

        userService.getUser(userAlias, new DataTaskObserver<User>() {
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
