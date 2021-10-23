package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask<GetStoryRequest, GetStoryResponse> {
    private static final String LOG_TAG = "GetStoryTask";

    public GetStoryTask(GetStoryRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetStoryResponse getResponse(GetStoryRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getStory(request);
    }

    @Override
    protected GetStoryResponse error(String message) {
        return new GetStoryResponse("Failed to get story" + message);
    }


}
