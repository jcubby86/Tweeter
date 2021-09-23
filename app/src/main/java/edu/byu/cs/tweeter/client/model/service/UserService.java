package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handlers.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.handlers.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.observers.AuthenticationObserver;
import edu.byu.cs.tweeter.client.model.service.observers.DataObserver;
import edu.byu.cs.tweeter.client.model.service.observers.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {
    public interface GetUserObserver extends DataObserver<User> {}
    public void getUser(String userAlias, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    public interface LoginObserver extends AuthenticationObserver {}
    public void login(String userAlias, String password, LoginObserver loginObserver) {
        LoginTask loginTask = new LoginTask(userAlias,
                password, new LoginHandler(loginObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public interface RegisterObserver extends AuthenticationObserver {}
    public void register(String firstName, String lastName, String userAlias, String password, String imageBytesBase64, RegisterObserver registerObserver) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                userAlias, password, imageBytesBase64, new RegisterHandler(registerObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }

    public interface LogoutObserver extends SimpleNotificationObserver {}
    public void logout(LogoutObserver observer){
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }
}
