package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.util.FakeData;

public class DynamoDBFeedDAO extends DynamoDBDAO implements FeedDAO{
    private static final String TABLE_NAME = "feed";
    private static final String PARTITION_USER_ALIAS = "alias";
    private static final String TIME_MILLIS = "time_millis";
    private static final String AUTHOR = "author";
    private static final String POST = "post";
    private static final String URLS = "urls";
    private static final String MENTIONS = "mentions";

    @SuppressWarnings("SimpleDateFormat")
    private final SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

    private final Table table = getTable(TABLE_NAME);

    @Override
    public List<Status> getFeed(String alias, int pageSize, Status lastStatus) {
        try {
            HashMap<String, String> nameMap = new HashMap<>();
            nameMap.put("#f", PARTITION_USER_ALIAS);

            HashMap<String, Object> valueMap = new HashMap<>();
            valueMap.put(":ffff", alias);

            QuerySpec spec = new QuerySpec().withKeyConditionExpression("#f = :ffff")
                    .withNameMap(nameMap).withValueMap(valueMap).withScanIndexForward(false)
                    .withMaxResultSize(pageSize);
            if (lastStatus != null) {
                spec = spec.withExclusiveStartKey(PARTITION_USER_ALIAS, alias,
                        TIME_MILLIS, toTimeMillis(lastStatus.getDatetime()));
            }

            ItemCollection<QueryOutcome> items = table.query(spec);
            List<Status> statuses = new ArrayList<>();
            UserDAO userDao = new DynamoDBUserDAO();
            for (Item item: items){
                statuses.add(new Status(item.getString(POST),
                        userDao.getUser(item.getString(AUTHOR)),
                        formatDateTime(item.getLong(TIME_MILLIS)),
                        item.getList(URLS), item.getList(MENTIONS)));
            }

            return statuses;
        } catch (Exception e){
            throw new DataAccessException("Could not get Feed");
        }
    }

    @Override
    public void postToFeeds(Status status, List<String> aliases) {

    }

    private void put(Status status, String userAlias) {
        try {
            Item item = new Item()
                    .withPrimaryKey(PARTITION_USER_ALIAS, userAlias,
                            TIME_MILLIS, toTimeMillis(status.getDatetime()))
                    .withString(AUTHOR, status.getUser().getAlias())
                    .withString(POST, status.getPost())
                    .withList(URLS, status.getUrls())
                    .withList(MENTIONS, status.getMentions());
            table.putItem(item);
        } catch (Exception e) {
            throw new DataAccessException("Could not add to Feed");
        }
    }

    private long toTimeMillis(String datetime) throws ParseException {
        Date date = statusFormat.parse(datetime);
        return date.getTime();
    }

    private String formatDateTime(long timeMillis){
        Date date = new Date(timeMillis);
        return statusFormat.format(date);
    }

    public static void main(String[] args) throws ParseException {
        DynamoDBFeedDAO dao = new DynamoDBFeedDAO();

//        Status status = new FakeData().getFakeStatuses().get(1);
//        dao.put(status, "@allen");

//        System.out.println(dao.getFeed("@allen", 10, null));

    }
}
