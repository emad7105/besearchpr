package be.heydari.dynamodb;

import be.heydari.commons.Commons;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Created by emad7105 on 26/01/2015.
 */
public class DynamoDbTest {


	@Test
	public void testInvertedIndex() {
		AmazonDynamoDBClient client = Commons.getDynamoDBObject();

		/*Put item into DynamoDB*/
		Map<String, AttributeValue> items = new HashMap<String, AttributeValue>();
		items.put("Key", new AttributeValue().withS("TestKey"));
		items.put("Location", new AttributeValue().withS("TestLocation2"));
		PutItemRequest itemRequest = new PutItemRequest().withTableName("InvertedIndex").withItem(items);
		client.putItem(itemRequest);

	}
}
