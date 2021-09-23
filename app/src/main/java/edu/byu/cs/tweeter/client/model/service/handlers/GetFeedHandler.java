package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetFeedTask.
 */
public class GetFeedHandler extends BackgroundTaskHandler<StatusService.GetFeedObserver> {

    public GetFeedHandler(StatusService.GetFeedObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get feed";
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        List<Status> statuses = (List<Status>) bundle.getSerializable(GetFeedTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(GetFeedTask.MORE_PAGES_KEY);

        observer.handleSuccess(statuses, hasMorePages);
    }


}
