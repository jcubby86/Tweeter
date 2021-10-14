package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handlers.CountTaskHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.UnfollowHandler;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.util.Pair;

public class FollowService extends Service {
    public void loadMoreFollowing(GetFollowingRequest request, PagedTaskObserver<User> getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(request, new GetFollowingHandler(getFollowingObserver));
        runTask(getFollowingTask);
    }

    public void loadMoreFollowers(GetFollowersRequest request, PagedTaskObserver<User> getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(request, new GetFollowersHandler(getFollowersObserver));
        runTask(getFollowersTask);
    }

    public void checkIsFollower(IsFollowerRequest request, DataTaskObserver<Boolean> observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(request, new IsFollowerHandler(observer));
        runTask(isFollowerTask);
    }

    public void unfollow(UnfollowRequest request, SimpleNotificationObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(request, new UnfollowHandler(observer));
        runTask(unfollowTask);
    }

    public void follow(FollowRequest request, SimpleNotificationObserver observer) {
        FollowTask followTask = new FollowTask(request, new FollowHandler(observer));
        runTask(followTask);
    }

    public void getCounts(CountRequest request, DataTaskObserver<Pair<Integer, Integer>> observer) {
        // Get count of most recently selected user's followers.
        CountTask countTask = new CountTask(request, new CountTaskHandler(observer));
        runTask(countTask);
    }


}
