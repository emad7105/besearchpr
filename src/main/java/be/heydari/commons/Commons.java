package be.heydari.commons;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * Created by emad7105 on 25/01/2015.
 */
public class Commons {

	public static final String HADOOP_BUCKET = "";
	public static final String ACCESS_KEY = "";
	public static final String SECRET_KEY = "";

	public static AmazonDynamoDBClient getDynamoDBObject() {
		AWSCredentials myCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(myCredentials);
		return client;
	}
}
