package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;

public class CountTask extends AuthorizedTask<CountRequest, CountResponse> {

    private static final String LOG_TAG = "AuthorizedTask";

    public CountTask(CountRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected CountResponse error(String message) {
        return new CountResponse("Failed to get followers/following counts" + message);
    }

    @Override
    protected CountResponse runTask(CountRequest request) throws IOException {
        return new CountResponse(20, 20);
    }

}
