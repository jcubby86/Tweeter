package edu.byu.cs.tweeter.server.sqs;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class UpdateFeedsMessage implements Serializable {
    private int id;
    private Status status;
    private List<String> followers;

    private UpdateFeedsMessage(){}

    public UpdateFeedsMessage(Status status, List<String> followers, int id) {
        this.status = status;
        this.followers = followers;
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
