package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.net.response.AuthenticationResponse;

public abstract class AuthenticationPresenter<RESPONSE extends AuthenticationResponse> extends Presenter<AuthenticateView>{

    public AuthenticationPresenter(AuthenticateView view) {
        super(view);
    }

    protected BackgroundTaskObserver<RESPONSE> getObserver(){
        return new BackgroundTaskObserver<RESPONSE>() {
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(RESPONSE response) {
                Cache.getInstance().setCurrUserAuthToken(response.getAuthToken());
                Cache.getInstance().setCurrUser(response.getUser());

                view.authenticateUser(response.getUser());
            }
        };
    }
}
