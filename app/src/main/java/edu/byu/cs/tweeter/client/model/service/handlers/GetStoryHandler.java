package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends BackgroundTaskHandler<StatusService.GetStoryObserver> {


    public GetStoryHandler(StatusService.GetStoryObserver observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to get story";
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        List<Status> statuses = (List<Status>) bundle.getSerializable(GetStoryTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(GetStoryTask.MORE_PAGES_KEY);

        observer.handleSuccess(statuses, hasMorePages);
    }
}
