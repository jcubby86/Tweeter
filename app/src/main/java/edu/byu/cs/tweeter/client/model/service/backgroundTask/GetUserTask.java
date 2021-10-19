package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthorizedTask<GetUserRequest, GetUserResponse> {

    private static final String LOG_TAG = "GetUserTask";

    public GetUserTask(GetUserRequest request, Handler messageHandler) {
        super(request, messageHandler);
    }

    @Override
    protected GetUserResponse error(String message) {
        return new GetUserResponse("Failed to get user's profile" + message);
    }

    @Override
    protected GetUserResponse runTask(GetUserRequest request) throws IOException {
        return new GetUserResponse(getUser(request));
    }

    private User getUser(GetUserRequest request) {
        return getFakeData().findUserByAlias(request.getAlias());
    }


}
