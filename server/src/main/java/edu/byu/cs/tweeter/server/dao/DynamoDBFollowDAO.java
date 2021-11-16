package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBFollowDAO extends DynamoDBDAO implements FollowDAO {
    private static final String FOLLOWS_INDEX = "followee_handle-follower_handle-index";
    private static final String FOLLOWER_HANDLE = "follower_handle";
    private static final String FOLLOWEE_HANDLE = "followee_handle";

    private final Table followsTable = getTable(FOLLOWS_TABLE);

    private QuerySpec getFollowingSpec(String follower_handle){
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#f", FOLLOWER_HANDLE);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ffff", follower_handle);
        return new QuerySpec().withKeyConditionExpression("#f = :ffff")
                .withNameMap(nameMap).withValueMap(valueMap);
    }

    public Pair<List<String>, Boolean> getFollowing(String follower_handle, int pageSize, String lastFolloweeAlias){
        try{
            QuerySpec spec = getFollowingSpec(follower_handle).withMaxResultSize(pageSize);
            if (lastFolloweeAlias != null){
                //Sort and partition switched with index
                spec.withExclusiveStartKey(FOLLOWEE_HANDLE, lastFolloweeAlias, FOLLOWER_HANDLE, follower_handle);
            }

            ItemCollection<QueryOutcome> following = followsTable.query(spec);
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

    public Pair<List<String>, Boolean> getFollowers(String followee_handle, int pageSize, String lastFollowerAlias){
        try{
            QuerySpec spec = getFollowersSpec(followee_handle).withMaxResultSize(pageSize);
            if (lastFollowerAlias != null){
                spec = spec.withExclusiveStartKey(FOLLOWER_HANDLE, lastFollowerAlias, FOLLOWEE_HANDLE, followee_handle);
            }

            ItemCollection<QueryOutcome> followers = followsTable.getIndex(FOLLOWS_INDEX).query(spec);
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
    public Pair<Integer, Integer> getCount(GetCountRequest request) {
        try{
            Table userTable = getTable(USER_TABLE);
            Item item = userTable.getItem(USER_ALIAS, request.getTargetUserAlias());

            return new Pair<>(
                    item.hasAttribute(FOLLOWER_COUNT) ? item.getInt(FOLLOWER_COUNT) : 0,
                    item.hasAttribute(FOLLOWING_COUNT) ? item.getInt(FOLLOWING_COUNT) : 0
            );
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not get counts");
        }
    }

    private void updateCount(String userAlias, String column, int change) throws DataAccessException {
        try{
            Table userTable = getTable(USER_TABLE);
            Item item = userTable.getItem(USER_ALIAS, userAlias);
            UpdateItemSpec spec = new UpdateItemSpec().withPrimaryKey(USER_ALIAS, userAlias)
                    .withUpdateExpression("set " + column + " = :a")
                    .withValueMap(new ValueMap().withInt(":a",
                            item.hasAttribute(column) ? item.getInt(column) + change : change));
            userTable.updateItem(spec);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not update Item");
        }
    }

    public void follow(FollowRequest request) {
        try {
            followsTable.putItem(new Item().withPrimaryKey(FOLLOWER_HANDLE, request.getFollowerAlias(),
                    FOLLOWEE_HANDLE, request.getFolloweeAlias()));
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not follow user");
        }
        updateCount(request.getFolloweeAlias(), FOLLOWER_COUNT, 1);
        updateCount(request.getFollowerAlias(), FOLLOWING_COUNT, 1);
    }

    public void unfollow(UnfollowRequest request) {
        try{
            followsTable.deleteItem(FOLLOWER_HANDLE, request.getFollowerAlias(),
                    FOLLOWEE_HANDLE, request.getFolloweeAlias());
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not unfollow user");
        }
        updateCount(request.getFolloweeAlias(), FOLLOWER_COUNT, -1);
        updateCount(request.getFollowerAlias(), FOLLOWING_COUNT, -1);
    }

    @Override
    public boolean isFollower(IsFollowerRequest request) {
        try{
            return followsTable.getItem(FOLLOWER_HANDLE, request.getFollower(), FOLLOWEE_HANDLE, request.getFollowee()) != null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not determine following relationship");
        }
    }

}
