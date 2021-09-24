package edu.byu.cs.tweeter.client.presenter.observers;

import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticateView extends View {
    void authenticateUser(User loggedInUser);
}
