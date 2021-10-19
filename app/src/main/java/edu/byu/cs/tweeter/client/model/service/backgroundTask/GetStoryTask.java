package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask<GetStoryRequest, GetStoryResponse> {

    private static final String LOG_TAG = "GetStoryTask";

    public GetStoryTask(GetStoryRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetStoryResponse getResponse(List<Status> items, boolean hasMorePages) {
        return new GetStoryResponse(items, hasMorePages);
    }

    @Override
    protected GetStoryResponse error(String message) {
        return new GetStoryResponse("Failed to get story" + message);
    }

    protected Pair<List<Status>, Boolean> getItems(GetStoryRequest request) {
        return getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
    }

}
