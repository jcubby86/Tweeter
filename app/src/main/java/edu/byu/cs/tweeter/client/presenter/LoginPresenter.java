package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;

public class LoginPresenter extends AuthenticatePresenter {

    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String userAlias, String password) {
        LoginRequest loginRequest = new LoginRequest(userAlias, password);
        getUserService().login(loginRequest, getObserver());
    }
}
