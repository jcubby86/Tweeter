package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBFollowDAO extends DynamoDBDAO implements FollowDAO {
    private static final String FOLLOWS_INDEX = "followee_handle-follower_handle-index";
    private static final String FOLLOWER_HANDLE = "follower_handle";
    private static final String FOLLOWEE_HANDLE = "followee_handle";

    private final Table table = getTable(FOLLOWS_TABLE);

    private QuerySpec getFollowingSpec(String follower_handle){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", FOLLOWER_HANDLE);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", follower_handle);
        return new QuerySpec().withKeyConditionExpression("#f = :ffff")
                .withNameMap(nameMap).withValueMap(valueMap);
    }

    public Pair<List<String>, Boolean> getFollowingPaged(String follower_handle, int pageSize, String lastFolloweeAlias){
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

            return new Pair<>(followingAliases, followingAliases.size() == pageSize);
        } catch (Exception e){
            System.out.println(e.getMessage());
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

    public Pair<List<String>, Boolean> getFollowersPaged(String followee_handle, int pageSize, String lastFollowerAlias){
        try{
            QuerySpec spec = getFollowersSpec(followee_handle).withMaxResultSize(pageSize);
            if (lastFollowerAlias != null){
                spec = spec.withExclusiveStartKey(FOLLOWER_HANDLE, lastFollowerAlias, FOLLOWEE_HANDLE, followee_handle);
            }

            ItemCollection<QueryOutcome> followers = table.getIndex(FOLLOWS_INDEX).query(spec);
            List<String> followerAliases = new ArrayList<>();

            for (Item item : followers) {
                followerAliases.add(item.getString(FOLLOWER_HANDLE));
            }

            return new Pair<>(followerAliases, followerAliases.size() == pageSize);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not retrieve followers");
        }
    }

    @Override
    public List<String> getFollowing(String follower_handle) {
        QuerySpec followingSpec = getFollowingSpec(follower_handle);
        ItemCollection<QueryOutcome> following = table.query(followingSpec);

        return getAliases(following, FOLLOWEE_HANDLE);
    }

    @Override
    public List<String> getFollowers(String followee_handle) {
        QuerySpec followersSpec = getFollowersSpec(followee_handle);
        ItemCollection<QueryOutcome> followers = table.getIndex(FOLLOWS_INDEX).query(followersSpec);

        return getAliases(followers, FOLLOWER_HANDLE);
    }

    @Override
    public Pair<Integer, Integer> getCount(String target_handle) {
        try{
            List<String> followers = getFollowers(target_handle);
            List<String> following = getFollowing(target_handle);

            return new Pair<>(followers.size(), following.size());
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not get counts");
        }
    }

    public void follow(FollowRequest request) {
        try {
            table.putItem(new Item().withPrimaryKey(FOLLOWER_HANDLE, request.getFollowerAlias(),
                    FOLLOWEE_HANDLE, request.getFolloweeAlias()));
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not put Item");
        }
    }

    public void unfollow(UnfollowRequest request) {
        try{
            table.deleteItem(FOLLOWER_HANDLE, request.getFollowerAlias(),
                    FOLLOWEE_HANDLE, request.getFolloweeAlias());
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not delete Item");
        }
    }

    @Override
    public boolean isFollower(String follower_handle, String followee_handle) {
        try{
            return table.getItem(FOLLOWER_HANDLE, follower_handle, FOLLOWEE_HANDLE, followee_handle) != null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not determine following relationship");
        }
    }

    private List<String> getAliases(ItemCollection<QueryOutcome> items, String target){
        List<String> aliases = new ArrayList<>();
        for (Item item : items){
            aliases.add(item.getString(target));
        }
        return aliases;
    }
}
