package org.example.blog;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.internal.IteratorSupport;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlogRepository {

    private final Table table;
    private final DynamoDB dynamoDB;

    public BlogRepository() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("blog");
    }

    public BatchWriteItemResult saveBlog(Blog blog) {
        List<Item> items = blog.posts().stream()
                .map(p -> toItem(blog.title(), p))
                .toList();
        TableWriteItems tableWriteItems = new TableWriteItems("blog")
                .withItemsToPut(items);
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
        return outcome.getBatchWriteItemResult();
    }

    public void savePost(String blogTitle, Post post) {
        table.putItem(toItem(blogTitle, post));
    }

    public List<Post> getPosts(String blogTitle) {
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("blog = :v_blog")
                .withValueMap(new ValueMap().withString(":v_blog", blogTitle));

        return readPosts(spec);
    }

    public List<Post> getFirstPost(String blogTitle) {
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("blog = :v_blog and post = :v_post")
                .withValueMap(
                        new ValueMap()
                                .withString(":v_blog", blogTitle)
                                .withString(":v_post", "post1")
                );

        return readPosts(spec);
    }

    private List<Post> readPosts(QuerySpec spec) {
        List<Post> posts = new ArrayList<>();
        for (Item item : table.query(spec)) {
            posts.add(fromItem(item));
        }
        return posts;
    }

    private Item toItem(String blogTitle, Post post) {
        return new Item()
                .withPrimaryKey("blog", blogTitle)
                .withString("post", post.title())
//                .with("date", post.date())
                .withString("text", post.text())
                .withString("author", post.author());
    }

    private Post fromItem(Item item) {
        return new Post(
                item.getString("post"),
                LocalDate.now(),
                item.getString("text"),
                item.getString("author"));
    }
}
