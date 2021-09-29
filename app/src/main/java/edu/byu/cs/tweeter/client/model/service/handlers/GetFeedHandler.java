package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetFeedTask.
 */
public class GetFeedHandler extends PagedHandler<Status> {

    public GetFeedHandler(PagedTaskObserver<Status> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get feed";
    }

}
