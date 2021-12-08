package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.sqs.SQSClient;

public class PostStatusHandler extends Handler<PostStatusRequest, PostStatusResponse> {
    private static final String POST_STATUS_QUEUE = "PostStatusQueue";

    @Override
    public PostStatusResponse handleRequest(PostStatusRequest input, Context context) {
        PostStatusResponse response = getStatusService().postStatus(input);
        if (response.isSuccess()) {
            String message = JsonSerializer.serialize(input.getStatus());
            SQSClient.sendMessage(POST_STATUS_QUEUE, message);

            System.out.println("Added status to SQS Queue");
        }
        return response;
    }
}
