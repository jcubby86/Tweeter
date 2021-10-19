package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;

/**
 * A base class for server responses.
 */
public abstract class Response implements Serializable {

    private boolean success;
    private String message;

    protected Response() {
        success = true;
    }

    protected Response(String message) {
        this.message = message;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
