package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.model.net.response.Response;

public interface BackgroundTaskObserver<RESPONSE extends Response> {
    void handleFailure(String message);
    void handleSuccess(RESPONSE response);
}
