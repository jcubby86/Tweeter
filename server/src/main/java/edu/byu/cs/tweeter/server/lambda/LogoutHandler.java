package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

public class LogoutHandler extends Handler<LogoutRequest, LogoutResponse>{
    @Override
    public LogoutResponse handleRequest(LogoutRequest input, Context context) {
        return getUserService().logout(input);
    }
}
