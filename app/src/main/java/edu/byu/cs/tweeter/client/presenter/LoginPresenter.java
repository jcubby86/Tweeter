package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticatePresenter<LoginPresenter.LoginView> {

    public interface LoginView extends AuthenticateView {}

    public LoginPresenter(LoginView view){
        super(view);
    }

    public void login(String userAlias, String password){
        userService.login(userAlias, password, getObserver());
    }
}
