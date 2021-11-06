package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class DynamoDBUserDao implements UserDao{
    private static final String TABLE_NAME = "users";
    private static final String PARTITION_ALIAS = "alias";

    private final DynamoDB dynamoDB;
    private final Table table;

    public DynamoDBUserDao() {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
            dynamoDB = new DynamoDB(client);
            table = dynamoDB.getTable(TABLE_NAME);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    @Override
    public void putUser(User user) {
        table.putItem(new Item().withPrimaryKey(PARTITION_ALIAS, user.getAlias())
                .withString("first_name", user.getFirstName())
                .withString("last_name", user.getLastName())
                .withString("image_url", user.getImageUrl()));
    }

    @Override
    public User getUser(String alias) {
        try {
            Item item = table.getItem(PARTITION_ALIAS, alias);
            return itemToUser(item);
        } catch (Exception e){
            throw new DataAccessException("Could not get User");
        }
    }

    @Override
    public List<User> getUserList(List<String> aliases) {
        List<User> users = new ArrayList<>();
        for (String alias: aliases){
            users.add(getUser(alias));
        }
        return users;
//        try {
//            TableKeysAndAttributes tableKeysAndAttributes = new TableKeysAndAttributes(TABLE_NAME);
//            tableKeysAndAttributes.addHashOnlyPrimaryKeys(PARTITION_ALIAS, aliases.toArray());
//
//            BatchGetItemOutcome outcome = dynamoDB.batchGetItem(tableKeysAndAttributes);
//
//            List<Item> items = outcome.getTableItems().get(TABLE_NAME);
//            List<User> users = new ArrayList<>();
//            for (Item item : items) {
//                users.add(itemToUser(item));
//            }
//            return users;
//        } catch (Exception e){
//            throw new DataAccessException("Could not get User");
//        }
    }

    private User itemToUser(Item item){
        return new User(item.getString("first_name"),
                item.getString("last_name"),
                item.getString(PARTITION_ALIAS),
                item.getString("image_url"));
    }

    public static void main(String[] args){
        UserDao userDao = new DynamoDBUserDao();
        FollowDao followDao = new DynamoDBFollowDao();

//        List<User> users = new FakeData().getFakeUsers();
//        for (User user: users){
//            userDao.putUser(user);
//        }

//        System.out.println(userDao.getUser("@asdfasd"));

//        List<String> aliases = followDao.getFollowers("@allen", 10, null);
//        List<User> users = userDao.getUserList(new ArrayList<>());
//        System.out.println(users);
    }
}
