package edu.byu.cs.tweeter.model.net.request;

public abstract class AuthenticationRequest extends Request{
    private String username;
    private String password;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    protected AuthenticationRequest() {}

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
