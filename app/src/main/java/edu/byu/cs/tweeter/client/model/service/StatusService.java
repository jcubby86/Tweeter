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
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;

public class StatusService extends Service {

    public void loadMoreStory(GetStoryRequest request, PagedTaskObserver<Status> observer) {
        GetStoryTask getStoryTask = new GetStoryTask(request, new GetStoryHandler(observer));
        runTask(getStoryTask);
    }

    public void loadMoreFeed(GetFeedRequest request, PagedTaskObserver<Status> observer) {
        GetFeedTask getFeedTask = new GetFeedTask(request, new GetFeedHandler(observer));
        runTask(getFeedTask);
    }

    public void postStatus(PostStatusRequest request, SimpleNotificationObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(request, new PostStatusHandler(observer));
        runTask(statusTask);
    }
}
