package org.example.book;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.List;

public class BookRepository {

    private final Table table;
    private final DynamoDB dynamoDB;

    public BookRepository() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("book");
    }

    public void persist(Book book) {
        table.putItem(toItem(book));
    }

    public String persist(List<Book> books) {
        TableWriteItems tableWriteItems = new TableWriteItems("employee")
                .withItemsToPut(books.stream()
                        .map(this::toItem)
                        .toList()
                );
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
        return outcome.getBatchWriteItemResult().toString();
    }

    public Book findById(int id) {
        Item item = table.getItem("id", id);
        if (item == null) {
            return null;
        }
        return fromItem(item);
    }

    private Item toItem(Book book) {
        return new Item()
                .withPrimaryKey("id", book.id())
                .withString("title", book.title())
                .withString("isbn", book.isbn())
                .withStringSet("authors", book.authors())
                .withJSON("vendorInfo", book.vendorInfo());
    }

    private Book fromItem(Item item) {
        return new Book(
                item.getNumber("id"),
                item.getString("title"),
                item.getString("isbn"),
                item.getStringSet("authors"),
                item.getJSON("vendorInfo")
        );
    }

    public String getFirstVendor(int id) {
        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey("id", id)
                .withProjectionExpression("isbn, vendorInfo.V01")
                .withConsistentRead(true);
        return table.getItem(spec).toJSON();
    }

}
