package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask<GetFeedRequest, GetFeedResponse> {

    private static final String LOG_TAG = "GetFeedTask";

    public GetFeedTask(GetFeedRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetFeedResponse getResponse(List<Status> items, boolean hasMorePages) {
        return new GetFeedResponse(items, hasMorePages);
    }

    @Override
    protected GetFeedResponse error(String message) {
        return new GetFeedResponse("Failed to get feed" + message);
    }

    protected Pair<List<Status>, Boolean> getItems(GetFeedRequest request) {
        return getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
    }

}
