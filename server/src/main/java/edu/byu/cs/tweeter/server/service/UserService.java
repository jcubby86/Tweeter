package edu.byu.cs.tweeter.server.service;

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
        return new GetUserResponse(getFakeData().findUserByAlias(request.getAlias()));
    }

    public LoginResponse login(LoginRequest request) {
        return new LoginResponse(getFakeData().getFirstUser(), getFakeData().getAuthToken());
    }

    public RegisterResponse register(RegisterRequest request) {
        return new RegisterResponse(getFakeData().getFirstUser(), getFakeData().getAuthToken());
    }

    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse();
    }
}
