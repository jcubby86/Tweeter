package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask<GetFeedRequest> {

    private static final String LOG_TAG = "GetFeedTask";

    public GetFeedTask(GetFeedRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    protected Pair<List<Status>, Boolean> getItems() {
        return getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
    }

}
