package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserDao {
    void putUser(User user);
    User getUser(String alias);
    List<User> getUserList(List<String> aliases);
}
