package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class UserService extends Service {
    public GetUserResponse getUser(GetUserRequest request) {
        request.checkRequest();
        return new GetUserResponse(getUserDao().getUser(request.getAlias()));
    }

    public LoginResponse login(LoginRequest request) {
        request.checkRequest();
        return new LoginResponse(getUserDao().login(request), getFakeData().getAuthToken());
    }

    public RegisterResponse register(RegisterRequest request) {
        request.checkRequest();
        User user = getUserDao().register(request);
        return new RegisterResponse(user, getFakeData().getAuthToken());
    }

    public LogoutResponse logout(LogoutRequest request) {
        request.checkRequest();
        return new LogoutResponse();
    }
}
