package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBDAO {
    private static final AmazonDynamoDB client;
    protected static final DynamoDB dynamoDB;

    static {
        try {
            client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
            dynamoDB = new DynamoDB(client);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    Table getTable(String tableName){
        try {
            return dynamoDB.getTable(tableName);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }
}
