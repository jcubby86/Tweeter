package edu.byu.cs.tweeter.server.dao;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message) {
        super(message);
    }
}
