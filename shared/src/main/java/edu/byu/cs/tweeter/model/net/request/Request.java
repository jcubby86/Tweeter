package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;

public abstract class Request implements Serializable {
    protected final String BAD_REQUEST = "[BadRequest]";
    protected Request(){}

    protected void badRequest(){
        throw new RuntimeException(BAD_REQUEST);
    }

    public abstract void checkRequest();
}
