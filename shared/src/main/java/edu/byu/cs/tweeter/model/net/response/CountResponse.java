package edu.byu.cs.tweeter.model.net.response;

public class CountResponse extends Response{

    private int followersCount;
    private int followingCount;

    private CountResponse(){}

    public CountResponse(String message) {
        super(message);
    }

    public CountResponse(int followersCount, int followingCount) {
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}
