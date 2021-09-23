package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;

public abstract class PagedHandler<D, T extends PagedTaskObserver<D>> extends BackgroundTaskHandler<PagedTaskObserver<D>> {
    public PagedHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        List<D> items = (List<D>) bundle.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(GetFeedTask.MORE_PAGES_KEY);

        observer.handleSuccess(items, hasMorePages);
    }
}
