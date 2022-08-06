package edu.byu.cs.tweeter.server.sqs;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.Collections;

public class SQSClient {

    public static final String SQS_QUEUE_PREFIX = "https://sqs.us-west-2.amazonaws.com/081910277536/";

    public static void sendMessage(String queueUrl, String messageBody){
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(SQS_QUEUE_PREFIX + queueUrl)
                .withMessageBody(messageBody)
                .withDelaySeconds(5);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);
    }

}