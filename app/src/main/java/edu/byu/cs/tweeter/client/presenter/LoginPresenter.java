package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticatePresenterObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter{

    private final UserService userService;
    private final LoginView view;

    public interface LoginView extends AuthenticatePresenterObserver {}

    public LoginPresenter(LoginView view){
        this.view = view;
        userService = new UserService();
    }

    public void login(String userAlias, String password){
        userService.login(userAlias, password, new UserService.LoginObserver() {
            @Override
            public void handleSuccess(User loggedInUser) {
                view.authenticateUser(loggedInUser);
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        });
    }
}
