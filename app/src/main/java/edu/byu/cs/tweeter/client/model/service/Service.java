package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;

public abstract class Service {
    public <RESPONSE extends Response> BackgroundTaskHandler<RESPONSE> getHandler(BackgroundTaskObserver<RESPONSE> observer){
        return new BackgroundTaskHandler<>(observer);
    }

    protected <REQUEST extends Request, RESPONSE extends Response> void runTask(BackgroundTask<REQUEST, RESPONSE> task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }
}
