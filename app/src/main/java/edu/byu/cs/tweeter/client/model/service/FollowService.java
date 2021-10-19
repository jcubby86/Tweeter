package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

public class FollowService extends Service {
    public void loadMoreFollowing(GetFollowingRequest request, BackgroundTaskObserver<GetFollowingResponse> getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(request,
                new BackgroundTaskHandler<>(getFollowingObserver));
        runTask(getFollowingTask);
    }

    public void loadMoreFollowers(GetFollowersRequest request, BackgroundTaskObserver<GetFollowersResponse> getFollowersObserver) {

        GetFollowersTask getFollowersTask = new GetFollowersTask(request,
                new BackgroundTaskHandler<>(getFollowersObserver));
        runTask(getFollowersTask);
    }

    public void checkIsFollower(IsFollowerRequest request, BackgroundTaskObserver<IsFollowerResponse> observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(request, new BackgroundTaskHandler<>(observer));
        runTask(isFollowerTask);
    }

    public void unfollow(UnfollowRequest request, BackgroundTaskObserver<UnfollowResponse> observer) {
        UnfollowTask unfollowTask = new UnfollowTask(request, new BackgroundTaskHandler<>(observer));
        runTask(unfollowTask);
    }

    public void follow(FollowRequest request, BackgroundTaskObserver<FollowResponse> observer) {
        FollowTask followTask = new FollowTask(request, new BackgroundTaskHandler<>(observer));
        runTask(followTask);
    }

    public void getCounts(CountRequest request, BackgroundTaskObserver<CountResponse> observer) {
        // Get count of most recently selected user's followers.
        CountTask countTask = new CountTask(request, new BackgroundTaskHandler<>(observer));
        runTask(countTask);
    }


}
