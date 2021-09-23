package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.handlers.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.handlers.PostStatusHandler;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.model.service.observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends Service{
    public interface GetStoryObserver extends PagedTaskObserver<Status> {}
    public void loadMoreOfStory(User user, int pageSize, Status lastStatus, GetStoryObserver observer) {
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetStoryHandler(observer));
        runTask(getStoryTask);
    }

    public interface GetFeedObserver extends PagedTaskObserver<Status> {}
    public void loadMoreOfFeed(User user, int pageSize, Status lastStatus, GetFeedObserver observer) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetFeedHandler(observer));
        runTask(getFeedTask);
    }

    public interface PostStatusObserver extends SimpleNotificationObserver {}
    public void postStatus(Status newStatus, PostStatusObserver observer){
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(observer));
        runTask(statusTask);
    }
}
