package edu.byu.cs.tweeter.model.net.request;

public abstract class AuthenticationRequest extends Request{
    private String alias;
    private String password;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    protected AuthenticationRequest() {}

    /**
     * Creates an instance.
     *
     * @param alias the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public AuthenticationRequest(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }


    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void checkRequest() {
        if (alias == null || password == null){
            badRequest();
        }
    }
}
