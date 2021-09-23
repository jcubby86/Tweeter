package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetFeedTask.
 */
public class GetFeedHandler extends PagedHandler<Status, StatusService.GetFeedObserver> {

    public GetFeedHandler(StatusService.GetFeedObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get feed";
    }

}
