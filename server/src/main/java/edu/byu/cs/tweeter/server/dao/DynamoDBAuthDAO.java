package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.HashMap;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class DynamoDBAuthDAO extends DynamoDBDAO implements AuthDAO {
    private static final String TABLE_NAME = "auth_tokens";
    private static final String INDEX_NAME = "alias-time_millis-index";
    private static final String PARTITION_AUTH_KEY = "auth_key";
    private static final String USER_ALIAS = "alias";
    private static final String TIME_MILLIS = "time_millis";
    private static final long EXPIRE_TIME = 120 * 60 * 1000;

    private final Table table = getTable(TABLE_NAME);

    public DynamoDBAuthDAO(DAOFactory factory) {
        super(factory);
    }

    @Override
    public boolean isAuthorized(AuthToken authToken) {
        try {
            Item item = table.getItem(PARTITION_AUTH_KEY, authToken.getToken());
            return item != null &&
                    System.currentTimeMillis() - item.getLong(TIME_MILLIS) < EXPIRE_TIME;
        } catch (Exception e){
            throw new DataAccessException("Unable to verify authentication of user");
        }
    }

    @Override
    public AuthToken getAuthToken(String alias) {
        try{
            deleteExpiredTokens(alias);

            AuthToken authToken = new AuthToken(alias);
            putToken(authToken);
            return authToken;
        } catch (Exception e){
            throw new DataAccessException("Unable to authenticate user");
        }
    }

    private void putToken(AuthToken authToken){
        Item item = new Item().withPrimaryKey(PARTITION_AUTH_KEY, authToken.getToken())
                .withString(USER_ALIAS, authToken.getUserAlias())
                .withNumber(TIME_MILLIS, authToken.getTimeMillis());
        table.putItem(item);
    }

    @Override
    public void deleteItem(String token) {
        try{
            table.deleteItem(PARTITION_AUTH_KEY, token);
        }catch (Exception e){
            throw new DataAccessException("Unable to Logout");
        }
    }

    private void deleteExpiredTokens(String alias){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", USER_ALIAS);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", alias);

        QuerySpec spec = new QuerySpec().withKeyConditionExpression("#f = :ffff")
                .withNameMap(nameMap).withValueMap(valueMap).withScanIndexForward(true);

        ItemCollection<QueryOutcome> items = table.getIndex(INDEX_NAME).query(spec);
        for (Item item: items){
            if (System.currentTimeMillis() - item.getLong(TIME_MILLIS) > EXPIRE_TIME){
                deleteItem(item.getString(PARTITION_AUTH_KEY));
            }
        }
    }


}
