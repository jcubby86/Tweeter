package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse extends Response{

    private boolean isFollower;

    private IsFollowerResponse() {
    }

    public IsFollowerResponse(boolean isFollower) {
        this.isFollower = isFollower;
    }

    public IsFollowerResponse(String message) {
        super(message);
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }
}