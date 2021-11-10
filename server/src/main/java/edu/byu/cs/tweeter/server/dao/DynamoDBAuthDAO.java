package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class DynamoDBAuthDAO implements AuthDAO {
    private static final String TABLE_NAME = "auth_tokens";
    private static final String PARTITION_AUTH_KEY = "auth_key";
    private static final String USER_ALIAS = "alias";
    private static final String TIME_MILLIS = "time_millis";

    private final Table table;

    public DynamoDBAuthDAO() {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
            DynamoDB dynamoDB = new DynamoDB(client);
            table = dynamoDB.getTable(TABLE_NAME);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    @Override
    public boolean isAuthorized(AuthToken authToken) {
        try {
            return table.getItem(PARTITION_AUTH_KEY, authToken.getToken()) != null;
        } catch (Exception e){
            throw new DataAccessException("Unable to verify authentication of user");
        }
    }

    @Override
    public AuthToken getAuthToken(String alias) {
        try{
            AuthToken authToken = new AuthToken(alias);
            Item item = new Item().withPrimaryKey(PARTITION_AUTH_KEY, authToken.getToken())
                    .withString(USER_ALIAS, alias).withNumber(TIME_MILLIS, authToken.getTimeMillis());
            table.putItem(item);
            return authToken;
        } catch (Exception e){
            throw new DataAccessException("Unable to authenticate user");
        }
    }

    @Override
    public void deleteItem(AuthToken authToken) {
        try{
            table.deleteItem(PARTITION_AUTH_KEY, authToken.getToken());
        }catch (Exception e){
            throw new DataAccessException("Unable to Logout");
        }
    }
}
