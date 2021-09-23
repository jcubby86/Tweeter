package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handlers.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.UnfollowHandler;
import edu.byu.cs.tweeter.client.model.service.observers.CountObserver;
import edu.byu.cs.tweeter.client.model.service.observers.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {
    public interface GetFollowingObserver extends ServiceObserver {
        void handleSuccess(List<User> followees, boolean hasMorePages);
    }
    public void loadMoreFollowing(User user, int pageSize, User lastFollowee, GetFollowingObserver getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }

    public interface GetFollowersObserver extends ServiceObserver {
        void handleSuccess(List<User> followers, boolean hasMorePages);
    }
    public void loadMoreFollowers(User user, int pageSize, User lastFollower, GetFollowersObserver getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new GetFollowersHandler(getFollowersObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }

    public interface IsFollowerObserver extends ServiceObserver {
        void handleSuccess(boolean isFollower);
    }
    public void checkIsFollower(User selectedUser, IsFollowerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    public interface UnfollowObserver extends SimpleNotificationObserver {
    }
    public void unfollow(User selectedUser, UnfollowObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }

    public interface FollowObserver extends SimpleNotificationObserver{
    }
    public void follow(User selectedUser, FollowObserver observer){
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }

    public interface GetFollowersCountObserver extends CountObserver {
    }
    public interface GetFollowingCountObserver extends CountObserver {
    }
    public void getCounts(User selectedUser, GetFollowersCountObserver getFollowersCountObserver, GetFollowingCountObserver getFollowingCountObserver){
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowersCountHandler(getFollowersCountObserver));
        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(getFollowingCountObserver));
        executor.execute(followingCountTask);
    }


}
