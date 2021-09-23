package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends PagedHandler<Status, StatusService.GetStoryObserver> {

    public GetStoryHandler(StatusService.GetStoryObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get story";
    }

}
