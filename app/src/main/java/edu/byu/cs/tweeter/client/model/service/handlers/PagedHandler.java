package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;

public abstract class PagedHandler<T> extends BackgroundTaskHandler<PagedTaskObserver<T>> {
    public PagedHandler(PagedTaskObserver<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle bundle) {
        List<T> items = (List<T>) bundle.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(GetFeedTask.MORE_PAGES_KEY);

        observer.handleSuccess(items, hasMorePages);
    }
}
