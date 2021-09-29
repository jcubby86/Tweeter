package edu.byu.cs.tweeter.client.model.service.handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.observers.DataTaskObserver;

public class IsFollowerHandler extends DataTaskHandler<Boolean> {

    public IsFollowerHandler(DataTaskObserver<Boolean> observer) {
        super(observer);
    }

    @Override
    protected String getMessage() {
        return "Failed to determine following relationship";
    }

    @Override
    protected Boolean getData(Bundle bundle) {
        return bundle.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
    }
}
