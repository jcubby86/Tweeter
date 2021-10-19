package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

public class LoginPresenter extends Presenter<AuthenticateView> {

    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String userAlias, String password) {
        LoginRequest loginRequest = new LoginRequest(userAlias, password);
        getUserService().login(loginRequest, new BackgroundTaskObserver<LoginResponse>() {
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(LoginResponse response) {
                Cache.getInstance().setCurrUserAuthToken(response.getAuthToken());
                Cache.getInstance().setCurrUser(response.getUser());

                view.authenticateUser(response.getUser());
            }
        });
    }
}
