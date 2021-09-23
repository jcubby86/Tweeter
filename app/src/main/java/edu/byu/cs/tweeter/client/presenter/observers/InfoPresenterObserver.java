package edu.byu.cs.tweeter.client.presenter.observers;

public interface InfoPresenterObserver extends PresenterObserver {
    void displayInfoMessage(String message);
    void clearInfoMessage();
}
