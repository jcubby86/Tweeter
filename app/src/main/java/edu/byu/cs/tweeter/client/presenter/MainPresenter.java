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
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.MainView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

public class MainPresenter extends Presenter<MainView> {

    private static final String LOG_TAG = "MainPresenter";

    public MainPresenter(MainView view) {
        super(view);
    }

    public void checkIsFollower(User selectedUser) {
        IsFollowerRequest request = new IsFollowerRequest(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), selectedUser);
        getFollowService().checkIsFollower(request, new BackgroundTaskObserver<IsFollowerResponse>() {
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(IsFollowerResponse response) {
                view.setFollowing(response.isFollower());
            }
        });
    }

    public void unfollow(User selectedUser) {
        view.displayInfoMessage("Removing " + selectedUser.getName() + "...");
        UnfollowRequest unfollowRequest = new UnfollowRequest(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
        getFollowService().unfollow(unfollowRequest, new BackgroundTaskObserver<UnfollowResponse>() {
            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayErrorMessage(message);
                view.enableFollowButton();
            }

            @Override
            public void handleSuccess(UnfollowResponse response) {
                view.clearInfoMessage();
                view.setFollowing(false);
                view.enableFollowButton();
            }
        });
    }

    public void follow(User selectedUser) {
        view.displayInfoMessage("Adding " + selectedUser.getName() + "...");
        FollowRequest followRequest = new FollowRequest(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
        getFollowService().follow(followRequest, new BackgroundTaskObserver<FollowResponse>() {
            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayErrorMessage(message);
                view.enableFollowButton();
            }

            @Override
            public void handleSuccess(FollowResponse response) {
                view.clearInfoMessage();
                view.setFollowing(true);
                view.enableFollowButton();
            }
        });
    }

    public void getCounts(User selectedUser) {
        GetCountRequest request = new GetCountRequest(Cache.getInstance().getCurrUserAuthToken(), selectedUser);
        getFollowService().getCounts(request, new BackgroundTaskObserver<GetCountResponse>() {
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(GetCountResponse response) {
                view.setCounts(response.getFollowersCount(), response.getFollowingCount());
            }
        });
    }

    public void logout() {
        LogoutRequest request = new LogoutRequest(Cache.getInstance().getCurrUserAuthToken());
        getUserService().logout(request, new BackgroundTaskObserver<LogoutResponse>() {
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(LogoutResponse response) {
                view.finishLogout();
            }
        });
    }

    public void postStatus(String post) {
        try {
            view.displayInfoMessage("Posting Status...");
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
            PostStatusRequest request = new PostStatusRequest(Cache.getInstance().getCurrUserAuthToken(), newStatus);
            getStatusService().postStatus(request, new BackgroundTaskObserver<PostStatusResponse>() {
                @Override
                public void handleFailure(String message) {
                    view.clearInfoMessage();
                    view.displayErrorMessage(message);
                }

                @Override
                public void handleSuccess(PostStatusResponse response) {
                    view.clearInfoMessage();
                    view.statusPostComplete();
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
