package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest extends AuthorizedRequest{
    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private  Status status;

    private PostStatusRequest() {
    }

    public PostStatusRequest(AuthToken authToken, Status status) {
        super(authToken);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
