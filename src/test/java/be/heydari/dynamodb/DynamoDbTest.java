package be.heydari.dynamodb;

import be.heydari.commons.Commons;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import org.junit.Test;

import java.util.*;

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
	
	@Test
	public void testUrlsByKeyword() {
		AmazonDynamoDBClient client = Commons.getDynamoDBObject();
		
		String tableName = "InvertedIndex";
		String word = "bert";

		
		List<String> links = new ArrayList<String>();
		Condition hashKeyCondition = new Condition()
				.withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(word));
		
		Map<String, Condition> keyConditions = new HashMap<String, Condition>();
		keyConditions.put("Key", hashKeyCondition);
		
		QueryRequest queryRequest = new QueryRequest()
				.withTableName(tableName)
				.withKeyConditions(keyConditions);
		
		QueryResult result = client.query(queryRequest);
		for (int i = 0 ; i<result.getItems().size(); i++) {
			links.add(result.getItems().get(i).get("Location").getS());
		}
		
		assertTrue(links.size()>0);

	}
}
