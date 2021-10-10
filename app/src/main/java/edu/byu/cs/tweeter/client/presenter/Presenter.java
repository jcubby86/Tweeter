package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observers.View;

public abstract class Presenter<T extends View> {



    private UserService userService;
    private FollowService followService;
    private StatusService statusService;
    protected T view;

    public Presenter(T view) {
        this.view = view;
    }

    public UserService getUserService() {
        if (userService == null){
            userService = new UserService();
        }
        return userService;
    }

    public FollowService getFollowService() {
        if (followService == null){
            followService = new FollowService();
        }
        return followService;
    }

    public StatusService getStatusService() {
        if (statusService == null){
            statusService = new StatusService();
        }
        return statusService;
    }

}
