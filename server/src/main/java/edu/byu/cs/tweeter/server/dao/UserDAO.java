package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.server.util.Pair;

public interface UserDAO {
    User getUser(GetUserRequest request);
    List<User> getUserList(List<String> aliases);
    Pair<User, Boolean> login(LoginRequest request);
    Pair<User, Boolean> register(RegisterRequest request);
}
