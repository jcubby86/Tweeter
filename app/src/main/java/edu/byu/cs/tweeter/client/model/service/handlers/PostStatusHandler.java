package edu.byu.cs.tweeter.client.model.service.handlers;

import edu.byu.cs.tweeter.client.model.service.StatusService;

public class PostStatusHandler extends SimpleNotificationHandler<StatusService.PostStatusObserver> {


    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to post status";
    }

}
