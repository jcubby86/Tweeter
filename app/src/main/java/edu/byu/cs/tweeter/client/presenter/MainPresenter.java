package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {

    private static final String LOG_TAG = "MainActivity";

    private final FollowService followService;
    private final StatusService statusService;
    private final UserService userService;
    private final MainView view;

    public interface MainView{
        void displayErrorMessage(String message);
        void setFollowing(boolean isFollowing);
        void enableFollowButton();
        void finishLogout();
        void statusPostComplete();
        void setFollowerCount(int count);
        void setFollowingCount(int count);

        void displayInfoMessage(String message);
        void clearInfoMessage();
    }

    public MainPresenter(MainView view) {
        this.view = view;
        followService = new FollowService();
        statusService = new StatusService();
        userService = new UserService();
    }

    public void checkIsFollower(User selectedUser) {
        followService.checkIsFollower(selectedUser, new FollowService.IsFollowerObserver() {
            @Override
            public void handleSuccess(boolean isFollower) {
                view.setFollowing(isFollower);
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        });
    }

    public void unfollow(User selectedUser) {
        view.displayInfoMessage("Removing " + selectedUser.getName() + "...");
        followService.unfollow(selectedUser, new FollowService.UnfollowObserver() {
            @Override
            public void handleSuccess() {
                view.clearInfoMessage();
                view.setFollowing(false);
                view.enableFollowButton();
            }
            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayErrorMessage(message);
                view.enableFollowButton();
            }
        });
    }

    public void follow(User selectedUser) {
        view.displayInfoMessage("Adding " + selectedUser.getName() + "...");
        followService.follow(selectedUser, new FollowService.FollowObserver() {
            @Override
            public void handleSuccess() {
                view.clearInfoMessage();
                view.setFollowing(true);
                view.enableFollowButton();
            }
            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayErrorMessage(message);
                view.enableFollowButton();
            }
        });
    }

    public void getCounts(User selectedUser) {
        followService.getCounts(selectedUser, new FollowService.GetFollowersCountObserver() {
            @Override
            public void handleSuccess(int followerCount) {
                view.setFollowerCount(followerCount);
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        }, new FollowService.GetFollowingCountObserver() {
            @Override
            public void handleSuccess(int followingCount) {
                view.setFollowingCount(followingCount);
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        });
    }

    public void logout(){
        userService.logout(new UserService.LogoutObserver() {
            @Override
            public void handleSuccess() {
                view.finishLogout();
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        });
    }

    public void postStatus(String post){
        try {
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
            statusService.postStatus(newStatus, new StatusService.PostStatusObserver() {
                @Override
                public void handleSuccess() {
                    view.statusPostComplete();
                }
                @Override
                public void handleFailure(String message) {
                    view.displayErrorMessage(message);
                }
            });
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.displayErrorMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }
    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }
    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }
}
