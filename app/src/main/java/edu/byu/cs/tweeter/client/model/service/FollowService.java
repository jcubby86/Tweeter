package edu.byu.cs.tweeter.client.model.service;

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
import edu.byu.cs.tweeter.client.model.service.observers.DataObserver;
import edu.byu.cs.tweeter.client.model.service.observers.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends Service{
    public interface GetFollowingObserver extends PagedObserver<User> {}
    public void loadMoreFollowing(User user, int pageSize, User lastFollowee, GetFollowingObserver getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        runTask(getFollowingTask);
    }

    public interface GetFollowersObserver extends PagedObserver<User> {}
    public void loadMoreFollowers(User user, int pageSize, User lastFollower, GetFollowersObserver getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new GetFollowersHandler(getFollowersObserver));
        runTask(getFollowersTask);
    }

    public interface IsFollowerObserver extends DataObserver<Boolean> {}
    public void checkIsFollower(User selectedUser, IsFollowerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        runTask(isFollowerTask);
    }

    public interface UnfollowObserver extends SimpleNotificationObserver {}
    public void unfollow(User selectedUser, UnfollowObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(observer));
        runTask(unfollowTask);
    }

    public interface FollowObserver extends SimpleNotificationObserver{}
    public void follow(User selectedUser, FollowObserver observer){
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(observer));
        runTask(followTask);
    }

    public interface GetFollowersDataObserver extends DataObserver<Integer> {}
    public interface GetFollowingDataObserver extends DataObserver<Integer> {}
    public void getCounts(User selectedUser, GetFollowersDataObserver getFollowersCountObserver, GetFollowingDataObserver getFollowingCountObserver){
                // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowersCountHandler(getFollowersCountObserver));
        runTask(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(getFollowingCountObserver));
        runTask(followingCountTask);
    }


}
