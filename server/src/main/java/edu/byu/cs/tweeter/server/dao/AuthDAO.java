package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthDAO {
    boolean isAuthorized(AuthToken authToken);
    AuthToken getAuthToken(String alias);
    void deleteItem(AuthToken authToken);
}
