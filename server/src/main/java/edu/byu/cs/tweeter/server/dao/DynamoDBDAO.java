package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import edu.byu.cs.tweeter.model.domain.User;

public class DynamoDBDAO {
    protected static final String FOLLOWS_TABLE = "follows";
    protected static final String USER_TABLE = "users";
    protected static final String FEED_TABLE = "feed";
    protected static final String STORY_TABLE = "story";
    protected static final String AUTH_TABLE = "auth_tokens";
    protected static final String USER_ALIAS = "alias";
    protected static final String FIRST_NAME = "first_name";
    protected static final String LAST_NAME = "last_name";
    protected static final String IMAGE_URL = "image_url";
    protected static final String TIME_MILLIS = "time_millis";

    private static final AmazonDynamoDB client;
    private static final DynamoDB dynamoDB;

    static {
        try {
            client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
            dynamoDB = new DynamoDB(client);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    protected static DynamoDB getDynamoDB() {
        return dynamoDB;
    }

    protected Table getTable(String tableName){
        try {
            return dynamoDB.getTable(tableName);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    protected User itemToUser(Item item){
        return new User(item.getString(FIRST_NAME),
                item.getString(LAST_NAME),
                item.getString(USER_ALIAS),
                item.getString(IMAGE_URL));
    }
}
