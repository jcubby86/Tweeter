package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.server.dao.FactoryManager;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.sqs.SQSClient;
import edu.byu.cs.tweeter.server.sqs.UpdateFeedsMessage;

public class PostUpdateFeedMessagesHandler implements RequestHandler<SQSEvent, Void> {
    private static final int PAGE_SIZE = 5;
    private static final String UPDATE_FEEDS_QUEUE = "UpdateFeedsQueue";

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            System.out.println("Got status from SQS Queue");
            Status status = JsonSerializer.deserialize(msg.getBody(), Status.class);
            FollowService followService = new FollowService(FactoryManager.getDAOFactory());

            List<String> followers = followService.sqsGetFollowers(
                    status.getAuthor(), null, PAGE_SIZE);

            int messagesSent = 0;

            while(followers.size() > 0){
                UpdateFeedsMessage updateFeedsMessage = new UpdateFeedsMessage(status, followers, ++messagesSent);
                String message = JsonSerializer.serialize(updateFeedsMessage);

                //TODO replace this with actual call to SQS Queue
                //SQSEvent sqsEvent = SQSClient.getSQSEvent(message);
                //new UpdateFeedsHandler().handleRequest(sqsEvent, null);
                //----------------------------------------------------------
                SQSClient.sendMessage(UPDATE_FEEDS_QUEUE, message);

                System.out.println("Added UpdateFeedsMessage " + messagesSent + " to SQS Queue.");

                followers = followService.sqsGetFollowers(status.getAuthor(), followers.get(followers.size() - 1), PAGE_SIZE);
            }

        }
        return null;
    }
}
