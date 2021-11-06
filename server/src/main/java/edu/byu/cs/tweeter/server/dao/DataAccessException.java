package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.service.Service;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message) {
        super(Service.INTERNAL_SERVER_ERROR + " " + message);
    }
}
