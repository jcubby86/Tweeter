package edu.byu.cs.tweeter.client.presenter.observers;

import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticatePresenterObserver extends PresenterObserver{
    void authenticateUser(User loggedInUser);
}
