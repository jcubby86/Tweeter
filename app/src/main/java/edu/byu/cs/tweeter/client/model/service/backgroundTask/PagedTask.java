package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.BackgroundTaskUtils;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public abstract class PagedTask<DATA, REQUEST extends PagedRequest<DATA>, RESPONSE extends PagedResponse<DATA>> extends AuthorizedTask<REQUEST, RESPONSE> {

    private static final String LOG_TAG = "PagedTask";

    public PagedTask(REQUEST request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected RESPONSE runTask(REQUEST request) throws IOException {
        RESPONSE response = getResponse(request);

        loadImages(response.getItems());

        return response;
    }


    private void loadImages(List<DATA> items) throws IOException {
        for (DATA item : items) {
            BackgroundTaskUtils.loadImage(convertItemToUser(item));
        }
    }

    protected abstract RESPONSE getResponse(REQUEST request);
    protected abstract User convertItemToUser(DATA item);

}
