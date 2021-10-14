package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask<GetStoryRequest> {

    private static final String LOG_TAG = "GetStoryTask";

    public GetStoryTask(GetStoryRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    protected Pair<List<Status>, Boolean> getItems() {
        return getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
    }

}
