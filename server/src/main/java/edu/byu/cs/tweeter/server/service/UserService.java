package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
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
        request.checkRequest();
        if (!getAuthDAO().isAuthorized(request.getAuthToken()))
            return new GetUserResponse("User not authorized");
        return new GetUserResponse(getUserDAO().getUser(request.getAlias()));
    }

    public LoginResponse login(LoginRequest request) {
        request.checkRequest();
        Pair<User, Boolean> data = getUserDAO().login(request);

        if (data.getSecond()) {
            return new LoginResponse(data.getFirst(), getAuthDAO().getAuthToken(request.getAlias()));
        } else {
            return new LoginResponse("Incorrect Password");
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        request.checkRequest();
        Pair<User, Boolean> data = getUserDAO().register(request);

        if (data.getSecond()) {
            return new RegisterResponse(data.getFirst(), getAuthDAO().getAuthToken(request.getAlias()));
        } else {
            return new RegisterResponse("Alias is already taken");
        }
    }

    public LogoutResponse logout(LogoutRequest request) {
        request.checkRequest();
        getAuthDAO().deleteItem(request.getAuthToken());
        return new LogoutResponse();
    }
}
