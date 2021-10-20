package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

public class LoginPresenter extends AuthenticationPresenter<LoginResponse> {

    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String userAlias, String password) {
        LoginRequest loginRequest = new LoginRequest(userAlias, password);
        getUserService().login(loginRequest, getObserver());
    }
}
