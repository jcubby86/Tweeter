package edu.byu.cs.tweeter.model.net.request;

public class RegisterRequest extends AuthenticationRequest{

    /**
     * The user's first name.
     */
    private String firstName;
    /**
     * The user's last name.
     */
    private String lastName;
    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private RegisterRequest() {
    }


    /**
     * Creates an instance.
     *  @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     * @param firstName first name
     * @param lastName last name
     * @param image image
     */
    public RegisterRequest(String username, String password, String firstName, String lastName, String image) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
