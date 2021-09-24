package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.observers.PagedTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status, StoryPresenter.StoryView> {

    public interface StoryView extends PagedView<Status> {}

    public StoryPresenter(StoryView view){
        super(view);
    }

    @Override
    protected void callService(User user, PagedTaskObserver<Status> observer) {
        statusService.loadMoreStory(user, PAGE_SIZE, lastItem, (StatusService.GetStoryObserver) observer);
    }

}
