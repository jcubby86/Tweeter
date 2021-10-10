package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;

public class LoginPresenter extends AuthenticatePresenter {

    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String userAlias, String password) {
        getUserService().login(userAlias, password, getObserver());
    }
}
