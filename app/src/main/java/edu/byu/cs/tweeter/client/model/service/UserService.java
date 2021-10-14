package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handlers.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;

public class UserService extends Service {

    public void getUser(GetUserRequest request, DataTaskObserver<User> getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(request, new GetUserHandler(getUserObserver));
        runTask(getUserTask);
    }

    public void login(LoginRequest request, DataTaskObserver<User> loginObserver) {
        LoginTask loginTask = new LoginTask(request, new LoginHandler(loginObserver));
        runTask(loginTask);
    }

    public void register(RegisterRequest request, DataTaskObserver<User> registerObserver) {
        RegisterTask registerTask = new RegisterTask(request, new RegisterHandler(registerObserver));
        runTask(registerTask);
    }

    public void logout(LogoutRequest request, SimpleNotificationObserver observer) {
        LogoutTask logoutTask = new LogoutTask(request, new LogoutHandler(observer));
        runTask(logoutTask);
    }
}
