package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;

public class DynamoDBUserDao implements UserDao{
    private static final String TABLE_NAME = "users";
    private static final String PARTITION_ALIAS = "alias";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String IMAGE_URL = "image_url";
    private static final String PASSWORD = "password";

    private static final String S3_BUCKET = "jacob-bastian.tweeter";
    private static final String S3_URL = "https://s3.us-west-2.amazonaws.com/";
    public static final String IMAGE_SUFFIX = "profile.png";

    private final Table table;

    public DynamoDBUserDao() {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
            DynamoDB dynamoDB = new DynamoDB(client);
            table = dynamoDB.getTable(TABLE_NAME);
        } catch (Exception e){
            throw new DataAccessException("Could not access Database");
        }
    }

    private void putUser(User user, String password) {
        table.putItem(new Item().withPrimaryKey(PARTITION_ALIAS, user.getAlias())
                .withString(FIRST_NAME, user.getFirstName())
                .withString(LAST_NAME, user.getLastName())
                .withString(IMAGE_URL, user.getImageUrl())
                .withString(PASSWORD, password));
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

    @Override
    public User login(LoginRequest request) {
        return getUser(request.getUsername());
    }

    @Override
    public User register(RegisterRequest request) {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        byte[] imageBytes = Base64.getDecoder().decode(request.getImage().getBytes());
        InputStream stream  = new ByteArrayInputStream(imageBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/png");

        s3.putObject(S3_BUCKET, request.getUsername() + IMAGE_SUFFIX, stream, metadata);

        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(),
                S3_URL + S3_BUCKET + "/" + request.getUsername() + IMAGE_SUFFIX);
        putUser(user, "");

        return user;
    }

    private User itemToUser(Item item){
        return new User(item.getString(FIRST_NAME),
                item.getString(LAST_NAME),
                item.getString(PARTITION_ALIAS),
                item.getString(IMAGE_URL));
    }

    public static void main(String[] args){
    }
}
