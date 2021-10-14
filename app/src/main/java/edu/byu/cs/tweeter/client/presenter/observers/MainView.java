package edu.byu.cs.tweeter.client.presenter.observers;

public interface MainView extends InfoView {
    void setFollowing(boolean isFollowing);

    void enableFollowButton();

    void finishLogout();

    void statusPostComplete();

    void setCounts(int followersCount, int followingCount);

}
