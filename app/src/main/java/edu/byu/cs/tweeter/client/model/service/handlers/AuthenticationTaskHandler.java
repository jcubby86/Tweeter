package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticationTask;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationTaskHandler<T extends DataTaskObserver<User>> extends DataTaskHandler<User, DataTaskObserver<User>>{
    public AuthenticationTaskHandler(T observer) {
        super(observer);
    }

    @Override
    protected User getData(Bundle bundle) {
        User authenticatedUser = (User) bundle.getSerializable(AuthenticationTask.USER_KEY);
        AuthToken authToken = (AuthToken) bundle.getSerializable(AuthenticationTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(authenticatedUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        return authenticatedUser;
    }
}
