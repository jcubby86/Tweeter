package edu.byu.cs.tweeter.client.presenter.observers;

public interface InfoView extends View {
    void displayInfoMessage(String message);
    void clearInfoMessage();
}
