package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;

public interface UserDao {
    User getUser(String alias);
    List<User> getUserList(List<String> aliases);
    User login(LoginRequest request);
    User register(RegisterRequest request);
}
