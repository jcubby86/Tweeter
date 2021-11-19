package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.sqs.PostUpdateFeedMessagesHandler;
import edu.byu.cs.tweeter.server.sqs.SQSClient;

public class PostStatusHandler extends Handler<PostStatusRequest, PostStatusResponse> {
    private static final String POST_STATUS_QUEUE = "https://sqs.us-west-2.amazonaws.com/081910277536/PostStatusQueue";

    @Override
    public PostStatusResponse handleRequest(PostStatusRequest input, Context context) {
        PostStatusResponse response = getStatusService().postStatus(input);

        String message = JsonSerializer.serialize(input.getStatus());
        //TODO replace this with actual call to SQS Queue
        SQSEvent sqsEvent = SQSClient.getSQSEvent(message);
        new PostUpdateFeedMessagesHandler().handleRequest(sqsEvent, null);
        //----------------------------------------------------------
        System.out.println("Added status to SQS Queue");

        return response;
    }
}
