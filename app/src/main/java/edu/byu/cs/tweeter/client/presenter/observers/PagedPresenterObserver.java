package edu.byu.cs.tweeter.client.presenter.observers;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface PagedPresenterObserver<T> extends InfoPresenterObserver {
    void displayMoreItems(List<T> items);
    void navigateToUser(User user);
    void removeLoadingFooter();
    void addLoadingFooter();
}
