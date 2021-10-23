package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask<GetFeedRequest, GetFeedResponse> {
    private static final String LOG_TAG = "GetFeedTask";

    public GetFeedTask(GetFeedRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetFeedResponse getResponse(GetFeedRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFeed(request);
    }

    @Override
    protected GetFeedResponse error(String message) {
        return new GetFeedResponse("Failed to get feed" + message);
    }

}
