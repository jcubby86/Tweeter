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
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends Service{
    public void loadMoreFollowing(User user, int pageSize, User lastFollowee, PagedTaskObserver<User> getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        runTask(getFollowingTask);
    }

    public void loadMoreFollowers(User user, int pageSize, User lastFollower, PagedTaskObserver<User> getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new GetFollowersHandler(getFollowersObserver));
        runTask(getFollowersTask);
    }

    public void checkIsFollower(User selectedUser, DataTaskObserver<Boolean> observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        runTask(isFollowerTask);
    }

    public void unfollow(User selectedUser, SimpleNotificationObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(observer));
        runTask(unfollowTask);
    }

    public void follow(User selectedUser, SimpleNotificationObserver observer){
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(observer));
        runTask(followTask);
    }

    public void getCounts(User selectedUser, DataTaskObserver<Integer> getFollowersCountObserver, DataTaskObserver<Integer> getFollowingCountObserver){
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
