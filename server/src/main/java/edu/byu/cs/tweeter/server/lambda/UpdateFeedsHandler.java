package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.server.dao.FactoryManager;
import edu.byu.cs.tweeter.server.service.StatusService;
import edu.byu.cs.tweeter.server.sqs.UpdateFeedsMessage;

public class UpdateFeedsHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            UpdateFeedsMessage message = JsonSerializer.deserialize(msg.getBody(), UpdateFeedsMessage.class);
            System.out.println("Got UpdateFeedsMessage " + message.getId() + " from SQS Queue");
            new StatusService(FactoryManager.getDAOFactory()).sqsPostStatus(message);
        }
        return null;
    }
}
