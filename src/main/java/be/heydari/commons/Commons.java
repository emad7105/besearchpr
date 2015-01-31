package be.heydari.commons;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by emad7105 on 25/01/2015.
 */
public class Commons {

	public static final String HADOOP_BUCKET = "besearchhadoop";
	public static final String ACCESS_KEY = "";
	public static final String SECRET_KEY = "";
	public static final Long 	TOTAL_PAGES = 10000l;
	public static final Integer PAGE_RANK_MAX_LOOP = 10;
	public static final Double 	PAGE_RANK_THRESHOLD = 0.001;


	public static AmazonDynamoDBClient getDynamoDBObject() {
		AWSCredentials myCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(myCredentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		client.setRegion(usWest2);
		return client;
	}
	
	public static AmazonS3Client getS3ClientObject() {
		AWSCredentials myCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonS3Client client = new AmazonS3Client(myCredentials);
		return client;
	}
}
