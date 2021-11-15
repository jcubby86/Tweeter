package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.tweeter.model.domain.Status;

public class DynamoDBStatusDAO extends DynamoDBDAO{
    private static final String PARTITION_USER_ALIAS = "alias";
    private static final String TIME_MILLIS = "time_millis";
    private static final String AUTHOR = "author";
    private static final String POST = "post";
    private static final String URLS = "urls";
    private static final String MENTIONS = "mentions";

    @SuppressWarnings("SimpleDateFormat")
    private final SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
    private final String TABLE_NAME;
    private final Table table;

    public DynamoDBStatusDAO(DAOFactory factory, LambdaLogger logger, String tableName){
        super(factory, logger);
        TABLE_NAME = tableName;
        table = getTable(TABLE_NAME);
    }

    private long toTimeMillis(String datetime) throws ParseException {
        Date date = statusFormat.parse(datetime);
        return date.getTime();
    }

    private String formatDateTime(long timeMillis){
        Date date = new Date(timeMillis);
        return statusFormat.format(date);
    }

    protected List<Status> doQuery(String alias, int pageSize, Status lastStatus){
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

            UserDAO userDao = factory.getUserDAO();
            for (Item item: items){
                statuses.add(new Status(item.getString(POST),
                        userDao.getUser(item.getString(AUTHOR)),
                        formatDateTime(item.getLong(TIME_MILLIS)),
                        item.getList(URLS), item.getList(MENTIONS)));
            }

            return statuses;
        } catch (Exception e){
            logger.log(e.getMessage());
            throw new DataAccessException("Could not get Feed");
        }
    }

    private Item getItem(Status status, String userAlias) throws ParseException {
        return new Item().withPrimaryKey(PARTITION_USER_ALIAS, userAlias,
                TIME_MILLIS, toTimeMillis(status.getDatetime()))
                .withString(AUTHOR, status.getUser().getAlias())
                .withString(POST, status.getPost())
                .withList(URLS, status.getUrls())
                .withList(MENTIONS, status.getMentions());
    }

    protected void doWrite(Status status, List<String> aliases){
        try{
            Set<String> set = new HashSet<>();
            List<Item> items = new ArrayList<>();
            for (String alias: aliases){
                if (!set.contains(alias)){
                    set.add(alias);
                    items.add(getItem(status, alias));
                }
            }

            TableWriteItems tableWriteItems = new TableWriteItems(TABLE_NAME)
                    .withItemsToPut(items.toArray(new Item[0]));
            BatchWriteItemOutcome outcome = getDynamoDB().batchWriteItem(tableWriteItems);

            while (outcome.getUnprocessedItems().size() > 0) {
                outcome = getDynamoDB().batchWriteItemUnprocessed(outcome.getUnprocessedItems());
            }

        } catch (Exception e){
            logger.log(e.getMessage());
            throw new DataAccessException("Could not post Status");
        }
    }
}
