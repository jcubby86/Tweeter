package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoDBStatusDAO extends DynamoDBDAO{
    private static final String AUTHOR = "author";
    private static final String POST = "post";
    private static final String URLS = "urls";
    private static final String MENTIONS = "mentions";

    @SuppressWarnings("SimpleDateFormat")
    private final SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
    private final String TABLE_NAME;
    private final Table statusTable;

    public DynamoDBStatusDAO(String tableName){
        TABLE_NAME = tableName;
        statusTable = getTable(TABLE_NAME);
    }

    private long toTimeMillis(String datetime) throws ParseException {
        Date date = statusFormat.parse(datetime);
        return date.getTime();
    }

    private String formatDateTime(long timeMillis){
        Date date = new Date(timeMillis);
        return statusFormat.format(date);
    }

    protected Pair<List<Status>, Boolean> doQuery(String alias, int pageSize, String lastItem){
        try {
            HashMap<String, String> nameMap = new HashMap<>();
            nameMap.put("#f", USER_ALIAS);

            HashMap<String, Object> valueMap = new HashMap<>();
            valueMap.put(":ffff", alias);

            QuerySpec spec = new QuerySpec().withKeyConditionExpression("#f = :ffff")
                    .withNameMap(nameMap).withValueMap(valueMap).withScanIndexForward(false)
                    .withMaxResultSize(pageSize);
            if (lastItem != null) {
                spec = spec.withExclusiveStartKey(USER_ALIAS, alias,
                        TIME_MILLIS, toTimeMillis(lastItem));
            }

            ItemCollection<QueryOutcome> items = statusTable.query(spec);
            List<Status> statuses = new ArrayList<>();

            Table userTable = getTable(USER_TABLE);
            for (Item item: items){
                Item userItem = userTable.getItem(USER_ALIAS, item.getString(AUTHOR));
                
                statuses.add(new Status(item.getString(POST),
                        itemToUser(userItem),
                        formatDateTime(item.getLong(TIME_MILLIS)),
                        item.getList(URLS), item.getList(MENTIONS)));
            }

            return new Pair<>(statuses, statuses.size() == pageSize);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not get Feed");
        }
    }

    private Item getItem(Status status, String userAlias) throws ParseException {
        return new Item().withPrimaryKey(USER_ALIAS, userAlias,
                TIME_MILLIS, toTimeMillis(status.getDatetime()))
                .withString(AUTHOR, status.getAuthor())
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
            System.out.println("Posted batch of Status to Feeds");

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new DataAccessException("Could not post Status");
        }
    }
}
