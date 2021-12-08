package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class StatusService extends Service {

    public void getStory(GetStoryRequest request, BackgroundTaskObserver<GetStoryResponse> observer) {
        GetStoryTask getStoryTask = new GetStoryTask(request, getHandler(observer));
        runTask(getStoryTask);
    }

    public void getFeed(GetFeedRequest request, BackgroundTaskObserver<GetFeedResponse> observer) {
        GetFeedTask getFeedTask = new GetFeedTask(request, getHandler(observer));
        runTask(getFeedTask);
    }

    public void postStatus(PostStatusRequest request, BackgroundTaskObserver<PostStatusResponse> observer) {
        System.out.println("In StatusService.postStatus");
        PostStatusTask statusTask = new PostStatusTask(request, getHandler(observer));
        runTask(statusTask);
    }
}
