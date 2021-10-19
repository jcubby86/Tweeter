package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class UserService extends Service {

    public void getUser(GetUserRequest request, BackgroundTaskObserver<GetUserResponse> getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(request, new BackgroundTaskHandler<>(getUserObserver));
        runTask(getUserTask);
    }

    public void login(LoginRequest request, BackgroundTaskObserver<LoginResponse> loginObserver) {
        LoginTask loginTask = new LoginTask(request, new BackgroundTaskHandler<>(loginObserver));
        runTask(loginTask);
    }

    public void register(RegisterRequest request, BackgroundTaskObserver<RegisterResponse> registerObserver) {
        RegisterTask registerTask = new RegisterTask(request, new BackgroundTaskHandler<>(registerObserver));
        runTask(registerTask);
    }

    public void logout(LogoutRequest request, BackgroundTaskObserver<LogoutResponse> observer) {
        LogoutTask logoutTask = new LogoutTask(request, new BackgroundTaskHandler<>(observer));
        runTask(logoutTask);
    }
}
