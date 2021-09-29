package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticatePresenter extends Presenter<AuthenticateView> {
    public AuthenticatePresenter(AuthenticateView view) {
        super(view);
    }

    protected DataTaskObserver<User> getObserver(){
        return new DataTaskObserver<User>(){
            @Override
            public void handleSuccess(User registeredUser) {
                view.authenticateUser(registeredUser);
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);

            }
        };
    }
}
