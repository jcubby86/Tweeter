package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.xspec.L;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBFollowDao implements FollowDao{
    private final String PARTITION_FOLLOWER = "follower_handle";
    private final String SORT_FOLLOWEE = "followee_handle";
    private final Table table;

    public DynamoDBFollowDao() throws DataAccessException {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
            DynamoDB dynamoDB = new DynamoDB(client);
            table = dynamoDB.getTable("follows");
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    public Pair<List<String>, Boolean> getFollowing(String follower_handle, int pageSize, String lastFolloweeAlias){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", PARTITION_FOLLOWER);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", follower_handle);

        QuerySpec spec = new QuerySpec().withKeyConditionExpression("#f = :ffff").withNameMap(nameMap).withValueMap(valueMap)
                .withMaxResultSize(pageSize);
        if (lastFolloweeAlias != null){
            //Sort and partition switched with index
            spec.withExclusiveStartKey(SORT_FOLLOWEE, lastFolloweeAlias, PARTITION_FOLLOWER, follower_handle);
        }

        ItemCollection<QueryOutcome> following = table.query(spec);
        List<String> followingAliases = new ArrayList<>();

        for (Item item: following){
            followingAliases.add((String) item.get(SORT_FOLLOWEE));
        }

        return new Pair<>(followingAliases, followingAliases.size() > 0);
    }

    public Pair<List<String>, Boolean> getFollowers(String followee_handle, int pageSize, String lastFollowerAlias){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", SORT_FOLLOWEE);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", followee_handle);

        QuerySpec spec = new QuerySpec().withKeyConditionExpression("#f = :ffff").withNameMap(nameMap).withValueMap(valueMap)
                .withScanIndexForward(false).withMaxResultSize(pageSize);
        if (lastFollowerAlias != null){
            spec = spec.withExclusiveStartKey(PARTITION_FOLLOWER, lastFollowerAlias, SORT_FOLLOWEE, followee_handle);
        }

        ItemCollection<QueryOutcome> followers = table.getIndex("followee_handle-follower_handle-index").query(spec);
        List<String> followerAliases = new ArrayList<>();

        for (Item item : followers) {
            followerAliases.add((String) item.get(PARTITION_FOLLOWER));
        }

        return new Pair<>(followerAliases, followerAliases.size() > 0);
    }

    public void putItem(String follower_handle, String followee_handle, String follower_name, String followee_name) throws DataAccessException {
        try {
            table.putItem(new Item().withPrimaryKey(PARTITION_FOLLOWER, follower_handle, SORT_FOLLOWEE, followee_handle)
                    .withString("follower_name", follower_name).withString("followee_name", followee_name));
        } catch (Exception e){
            throw new DataAccessException("Could not put Item");
        }
    }

    public void deleteItem(String follower_handle, String followee_handle) throws DataAccessException {
        try{
            table.deleteItem(PARTITION_FOLLOWER, follower_handle, SORT_FOLLOWEE, followee_handle);
        }catch (Exception e){
            throw new DataAccessException("Could not delete Item");
        }
    }
}
