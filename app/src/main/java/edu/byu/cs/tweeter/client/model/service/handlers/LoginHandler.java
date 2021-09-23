package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticationTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginHandler extends Handler {
    private final UserService.LoginObserver observer;

    public LoginHandler(UserService.LoginObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);
        if (success) {
            User loggedInUser = (User) msg.getData().getSerializable(AuthenticationTask.USER_KEY);
            AuthToken authToken = (AuthToken) msg.getData().getSerializable(AuthenticationTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            observer.handleSuccess(loggedInUser);
        } else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY)) {
            String message = "Failed to login: " + msg.getData().getString(LoginTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
            String message = "Failed to login because of exception: " + ex.getMessage();
            observer.handleFailure(message);
        }
    }
}
