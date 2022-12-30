package org.example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;

public class Main {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    static String tableName = "employee";

    public static void main(String[] args) {
        retrieveItem();
    }

    private static void retrieveItem() {
        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem("id", 1);

        System.out.println("Printing item after retrieving it....");
        System.out.println(item.toJSONPretty());
    }
}