package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handlers.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.handlers.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends Service{

    public interface GetUserObserver extends DataTaskObserver<User> {}
    public void getUser(String userAlias, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler(getUserObserver));
        runTask(getUserTask);
    }

    public interface LoginObserver extends DataTaskObserver<User> {}
    public void login(String userAlias, String password, LoginObserver loginObserver) {
        LoginTask loginTask = new LoginTask(userAlias,
                password, new LoginHandler(loginObserver));
        runTask(loginTask);
    }

    public interface RegisterObserver extends DataTaskObserver<User> {}
    public void register(String firstName, String lastName, String userAlias, String password, String imageBytesBase64, RegisterObserver registerObserver) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                userAlias, password, imageBytesBase64, new RegisterHandler(registerObserver));
        runTask(registerTask);
    }

    public interface LogoutObserver extends SimpleNotificationObserver {}
    public void logout(LogoutObserver observer){
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        runTask(logoutTask);
    }
}
