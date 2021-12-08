package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;

public abstract class BackgroundTask<REQUEST extends Request, RESPONSE extends Response> implements Runnable {

    private static final String LOG_TAG = "BackgroundTask";

    public static final String RESPONSE_KEY = "response";

    /**
     * Message handler that will receive task results.
     */
    private final REQUEST request;
    private final Handler messageHandler;

    private ServerFacade serverFacade;

    public BackgroundTask(REQUEST request, Handler messageHandler) {
        this.messageHandler = messageHandler;
        this.request = request;
    }

    @Override
    public void run() {
        RESPONSE response;
        try {
            System.out.println("running task!");
            response = runTask(request);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            response = error(" because of exception: " + ex.getMessage());
        }
        sendMessage(response);
    }

    private void sendMessage(RESPONSE response) {
        Bundle msgBundle = new Bundle();
        msgBundle.putSerializable(RESPONSE_KEY, response);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);

        System.out.println("message sent");
    }

    protected ServerFacade getServerFacade() {
        if (serverFacade == null){
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }

    protected abstract RESPONSE error(String message);
    protected abstract RESPONSE runTask(REQUEST request) throws IOException, TweeterRemoteException;
}
