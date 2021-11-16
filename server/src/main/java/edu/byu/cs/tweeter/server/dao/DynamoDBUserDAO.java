package edu.byu.cs.tweeter.server.dao;

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
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.server.security.PasswordEncryptor;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBUserDAO extends DynamoDBDAO implements UserDAO {
    private static final String PASSWORD = "password";


    private static final String S3_URL = "https://s3.us-west-2.amazonaws.com/";
    private static final String S3_BUCKET = "jacob-bastian.tweeter";
    private static final String IMAGE_SUFFIX = "profile.png";

    private final Table userTable = getTable(USER_TABLE);

    private void putUser(User user, String password) {
        userTable.putItem(new Item().withPrimaryKey(USER_ALIAS, user.getAlias())
                .withString(FIRST_NAME, user.getFirstName())
                .withString(LAST_NAME, user.getLastName())
                .withString(IMAGE_URL, user.getImageUrl())
                .withString(PASSWORD, password)
                .withInt(FOLLOWER_COUNT, 0)
                .withInt(FOLLOWING_COUNT,0));
    }

    private Item getItem(String alias){
        return userTable.getItem(USER_ALIAS, alias);
    }

    private User getUser(String alias) {
        try {
            return itemToUser(getItem(alias));
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not get user");
        }
    }

    @Override
    public User getUser(GetUserRequest request){
        return getUser(request.getAlias());
    }

    @Override
    public List<User> getUserList(List<String> aliases) {
        try {
            List<User> users = new ArrayList<>();
            for (String alias: aliases){
                users.add(getUser(alias));
            }
            return users;

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not get users");
        }
    }

    @Override
    public Pair<User, Boolean> login(LoginRequest request) {
        try {
            Item item = getItem(request.getAlias());
            boolean success = PasswordEncryptor.validatePassword(request.getPassword(), item.getString(PASSWORD));
            return new Pair<>(itemToUser(item), success);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not login user");
        }

    }

    private String uploadImage(RegisterRequest request) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-west-2").build();

        byte[] imageBytes = Base64.getDecoder().decode(request.getImage().getBytes());
        InputStream stream  = new ByteArrayInputStream(imageBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/png");

        s3.putObject(S3_BUCKET, request.getAlias() + IMAGE_SUFFIX, stream, metadata);

        return S3_URL + S3_BUCKET + "/" + request.getAlias() + IMAGE_SUFFIX;
    }

    @Override
    public Pair<User, Boolean> register(RegisterRequest request) {
        try {
            if (getItem(request.getAlias()) != null){
                return new Pair<>(null, false);
            }

            String imageURL = uploadImage(request);

            User user = new User(request.getFirstName(), request.getLastName(),
                    request.getAlias(), imageURL);
            String hashedPass = PasswordEncryptor.generateStrongPasswordHash(request.getPassword());
            putUser(user, hashedPass);
            return new Pair<>(user, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not register user");
        }
    }


}
