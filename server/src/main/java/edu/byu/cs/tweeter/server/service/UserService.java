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
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.util.Pair;

public class UserService extends Service {

    public UserService(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        if (isAuthorized(request)) {
            System.out.println("Retrieving user " + request.getAlias() + " for user " + request.getAuthToken().getUserAlias());
            return new GetUserResponse(getUserDAO().getUser(request));
        } else {
            logUnauthorized(request);
            return new GetUserResponse(NOT_AUTHORIZED);
        }
    }

    public LoginResponse login(LoginRequest request) {
        request.checkRequest();
        Pair<User, Boolean> data = getUserDAO().login(request);

        if (data.getSecond()) {
            System.out.println("Logging in " + request.getAlias());
            return new LoginResponse(data.getFirst(), getAuthDAO().getAuthToken(request.getAlias()));
        } else {
            System.out.println("Incorrect Password");
            return new LoginResponse("Incorrect Password");
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        request.checkRequest();
        Pair<User, Boolean> data = getUserDAO().register(request);

        if (data.getSecond()) {
            System.out.println("Registering " + request.getAlias());
            return new RegisterResponse(data.getFirst(), getAuthDAO().getAuthToken(request.getAlias()));
        } else {
            System.out.println("Alias is already taken");
            return new RegisterResponse("Alias is already taken");
        }
    }

    public LogoutResponse logout(LogoutRequest request) {
        request.checkRequest();
        System.out.println("Logging out " + request.getAuthToken().getUserAlias());
        getAuthDAO().deleteItem(request.getAuthToken().getToken());
        return new LogoutResponse();
    }
}
