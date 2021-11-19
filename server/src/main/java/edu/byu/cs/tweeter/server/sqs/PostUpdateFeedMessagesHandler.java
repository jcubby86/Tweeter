package edu.byu.cs.tweeter.server.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.server.dao.FactoryManager;
import edu.byu.cs.tweeter.server.service.FollowService;
import edu.byu.cs.tweeter.server.util.Pair;

public class PostUpdateFeedMessagesHandler implements RequestHandler<SQSEvent, Void> {
    private static final int PAGE_SIZE = 25;
    private static final String UPDATE_FEEDS_QUEUE = "https://sqs.us-west-2.amazonaws.com/081910277536/UpdateFeedsQueue";

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            System.out.println("Got status from SQS Queue");
            Status status = JsonSerializer.deserialize(msg.getBody(), Status.class);
            FollowService followService = new FollowService(FactoryManager.getDAOFactory());

            List<String> followers = followService.sqsGetFollowers(
                    status.getUser().getAlias(), null, PAGE_SIZE);

            while(followers.size() > 0){
                UpdateFeedsMessage updateFeedsMessage = new UpdateFeedsMessage(status, followers);
                String message = JsonSerializer.serialize(updateFeedsMessage);

                //TODO replace this with actual call to SQS Queue
                SQSEvent sqsEvent = SQSClient.getSQSEvent(message);
                new UpdateFeedsHandler().handleRequest(sqsEvent, null);
                //----------------------------------------------------------
                System.out.println("Added UpdateFeedsMessage to SQS Queue");

                followers = followService.sqsGetFollowers(status.getUser().getAlias(), followers.get(followers.size() - 1), PAGE_SIZE);
            }

        }
        return null;
    }
}
