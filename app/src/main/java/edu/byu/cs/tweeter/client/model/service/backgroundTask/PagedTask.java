package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.BackgroundTaskUtils;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<DATA, REQUEST extends PagedRequest<DATA>, RESPONSE extends PagedResponse<DATA>> extends AuthorizedTask<REQUEST, RESPONSE> {

    private static final String LOG_TAG = "PagedTask";

    public PagedTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected RESPONSE runTask(REQUEST request) throws IOException {
        Pair<List<DATA>, Boolean> pageOfUsers = getItems(request);

        List<DATA> items = pageOfUsers.getFirst();
        boolean hasMorePages = pageOfUsers.getSecond();

        loadImages(items);

        return getResponse(items, hasMorePages);
    }

    private void loadImages(List<DATA> items) throws IOException {
        for (DATA item : items) {
            BackgroundTaskUtils.loadImage(convertItemToUser(item));
        }
    }

    protected abstract RESPONSE getResponse(List<DATA> items, boolean hasMorePages);
    protected abstract Pair<List<DATA>, Boolean> getItems(REQUEST request);
    protected abstract User convertItemToUser(DATA item);

}
