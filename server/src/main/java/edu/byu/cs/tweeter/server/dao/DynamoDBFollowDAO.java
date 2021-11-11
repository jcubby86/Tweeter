package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBFollowDAO extends DynamoDBDAO implements FollowDAO {
    private static final String TABLE_NAME = "follows";
    private static final String INDEX_NAME = "followee_handle-follower_handle-index";
    private static final String FOLLOWER_HANDLE = "follower_handle";
    private static final String FOLLOWEE_HANDLE = "followee_handle";

    private final Table table = getTable(TABLE_NAME);

    private QuerySpec getFollowingSpec(String follower_handle){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", FOLLOWER_HANDLE);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", follower_handle);
        return new QuerySpec().withKeyConditionExpression("#f = :ffff")
                .withNameMap(nameMap).withValueMap(valueMap);
    }

    public List<String> getFollowing(String follower_handle, int pageSize, String lastFolloweeAlias){
        try{
            QuerySpec spec = getFollowingSpec(follower_handle).withMaxResultSize(pageSize);
            if (lastFolloweeAlias != null){
                //Sort and partition switched with index
                spec.withExclusiveStartKey(FOLLOWEE_HANDLE, lastFolloweeAlias, FOLLOWER_HANDLE, follower_handle);
            }

            ItemCollection<QueryOutcome> following = table.query(spec);
            List<String> followingAliases = new ArrayList<>();

            for (Item item: following){
                followingAliases.add(item.getString(FOLLOWEE_HANDLE));
            }

            return followingAliases;
        } catch (Exception e){
            throw new DataAccessException("Could not retrieve following");
        }
    }

    private QuerySpec getFollowersSpec(String followee_handle){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", FOLLOWEE_HANDLE);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", followee_handle);

        return new QuerySpec().withKeyConditionExpression("#f = :ffff")
                .withNameMap(nameMap).withValueMap(valueMap);
    }

    public List<String> getFollowers(String followee_handle, int pageSize, String lastFollowerAlias){
        try{
            QuerySpec spec = getFollowersSpec(followee_handle).withMaxResultSize(pageSize);
            if (lastFollowerAlias != null){
                spec = spec.withExclusiveStartKey(FOLLOWER_HANDLE, lastFollowerAlias, FOLLOWEE_HANDLE, followee_handle);
            }

            ItemCollection<QueryOutcome> followers = table.getIndex(INDEX_NAME).query(spec);
            List<String> followerAliases = new ArrayList<>();

            for (Item item : followers) {
                followerAliases.add(item.getString(FOLLOWER_HANDLE));
            }

            return followerAliases;
        } catch (Exception e){
            throw new DataAccessException("Could not retrieve followers");
        }
    }

    @Override
    public Pair<Integer, Integer> getCount(String target_handle) {
        try{
            QuerySpec followersSpec = getFollowersSpec(target_handle);
            ItemCollection<QueryOutcome> followers = table.getIndex(INDEX_NAME).query(followersSpec);
            QuerySpec followingSpec = getFollowingSpec(target_handle);
            ItemCollection<QueryOutcome> following = table.query(followingSpec);

            int numFollowers = 0;
            int numFollowing = 0;

            for (Item ignored : followers){
                numFollowers += 1;
            }

            for (Item ignored : following){
                numFollowing += 1;
            }

            return new Pair<>(numFollowers, numFollowing);
        } catch (Exception e){
            throw new DataAccessException("Could not get counts");
        }
    }

    public void putItem(String follower_handle, String followee_handle) {
        try {
            table.putItem(new Item().withPrimaryKey(FOLLOWER_HANDLE, follower_handle, FOLLOWEE_HANDLE, followee_handle));
        } catch (Exception e){
            throw new DataAccessException("Could not put Item");
        }
    }

    public void deleteItem(String follower_handle, String followee_handle) {
        try{
            table.deleteItem(FOLLOWER_HANDLE, follower_handle, FOLLOWEE_HANDLE, followee_handle);
        }catch (Exception e){
            throw new DataAccessException("Could not delete Item");
        }
    }

    @Override
    public boolean isFollower(String follower_handle, String followee_handle) {
        try{
            return table.getItem(FOLLOWER_HANDLE, follower_handle, FOLLOWEE_HANDLE, followee_handle) != null;
        }catch (Exception e){
            throw new DataAccessException("Could not delete Item");
        }
    }
}
