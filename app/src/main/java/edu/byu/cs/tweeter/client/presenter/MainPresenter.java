package edu.byu.cs.tweeter.client.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.presenter.observers.MainView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.util.Pair;

public class MainPresenter extends Presenter<MainView> {

    private static final String LOG_TAG = "MainPresenter";

    public MainPresenter(MainView view) {
        super(view);
    }

    public void checkIsFollower(User selectedUser) {
        IsFollowerRequest request = new IsFollowerRequest(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), selectedUser);
        getFollowService().checkIsFollower(request, new DataTaskObserver<Boolean>() {
            @Override
            public void handleSuccess(Boolean isFollower) {
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
        UnfollowRequest unfollowRequest = new UnfollowRequest(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
        getFollowService().unfollow(unfollowRequest, new SimpleNotificationObserver() {
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
        FollowRequest followRequest = new FollowRequest(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
        getFollowService().follow(followRequest, new SimpleNotificationObserver() {
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
        CountRequest request = new CountRequest(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
        getFollowService().getCounts(request, new DataTaskObserver<Pair<Integer, Integer>>() {
            @Override
            public void handleSuccess(Pair<Integer, Integer> data) {
                view.setCounts(data.getFirst(), data.getSecond());
            }

            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        });
    }

    public void logout() {
        LogoutRequest request = new LogoutRequest(Cache.getInstance().getCurrUserAuthToken());
        getUserService().logout(request, new SimpleNotificationObserver() {
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

    public void postStatus(String post) {
        try {
            view.displayInfoMessage("Posting Status...");
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
            PostStatusRequest request = new PostStatusRequest(Cache.getInstance().getCurrUserAuthToken(), newStatus);
            getStatusService().postStatus(request, new SimpleNotificationObserver() {
                @Override
                public void handleSuccess() {
                    view.clearInfoMessage();
                    view.statusPostComplete();
                }

                @Override
                public void handleFailure(String message) {
                    view.clearInfoMessage();
                    view.displayErrorMessage(message);
                }
            });
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getFormattedDateTime() throws ParseException {
         SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(Objects.requireNonNull(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8))));
    }

    private List<String> parseURLs(String post) {
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

    private List<String> parseMentions(String post) {
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

    private int findUrlEndIndex(String word) {
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
