package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse extends Response{

    private boolean follower;

    private IsFollowerResponse() {
    }

    public IsFollowerResponse(boolean follower) {
        this.follower = follower;
    }

    public IsFollowerResponse(String message) {
        super(message);
    }

    public boolean isFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }
}
