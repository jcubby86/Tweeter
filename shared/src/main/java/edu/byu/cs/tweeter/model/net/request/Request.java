package edu.byu.cs.tweeter.model.net.request;

public abstract class Request {
    protected final String BAD_REQUEST = "[BadRequest]";
    protected Request(){}

    protected void badRequest(){
        throw new RuntimeException(BAD_REQUEST);
    }

    public abstract void checkRequest();
}
