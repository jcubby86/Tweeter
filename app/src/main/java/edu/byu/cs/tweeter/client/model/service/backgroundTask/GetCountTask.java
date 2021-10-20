package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;

public class GetCountTask extends AuthorizedTask<GetCountRequest, GetCountResponse> {

    private static final String LOG_TAG = "AuthorizedTask";

    public GetCountTask(GetCountRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetCountResponse error(String message) {
        return new GetCountResponse("Failed to get followers/following counts" + message);
    }

    @Override
    protected GetCountResponse runTask(GetCountRequest request) throws IOException {
        return getFollowService().getCounts(request);
    }

}
