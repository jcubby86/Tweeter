package edu.byu.cs.tweeter.server.dao;

public class DataAccessException extends RuntimeException {
    public static final String INTERNAL_SERVER_ERROR = "[InternalServerError]";

    public DataAccessException(String message) {
        super(INTERNAL_SERVER_ERROR + " " + message);
    }
}
