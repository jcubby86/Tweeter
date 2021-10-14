package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.BackgroundTaskUtils;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<DATA, T extends PagedRequest<DATA>> extends AuthorizedTask<T> {

    private static final String LOG_TAG = "PagedTask";

    public static final String ITEMS_KEY = "followees";
    public static final String MORE_PAGES_KEY = "more-pages";

    private List<DATA> items;
    private boolean hasMorePages;

    public PagedTask(T request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected void runTask() throws IOException {
        Pair<List<DATA>, Boolean> pageOfUsers = getItems();

        items = pageOfUsers.getFirst();
        hasMorePages = pageOfUsers.getSecond();

        loadImages(items);
    }

    protected abstract Pair<List<DATA>, Boolean> getItems();

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    private void loadImages(List<DATA> items) throws IOException {
        for (DATA item : items) {
            BackgroundTaskUtils.loadImage(convertItemToUser(item));
        }
    }

    protected abstract User convertItemToUser(DATA item);
}
