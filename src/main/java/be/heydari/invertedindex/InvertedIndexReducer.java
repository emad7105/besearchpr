/**
 * @author Emad Heydari Beni
 * From: http://hadoop.apache.org/common/docs/r1.0.3/mapred_tutorial.html
 */

package be.heydari.invertedindex;

import be.heydari.commons.Commons;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InvertedIndexReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	private AmazonDynamoDBClient client;

	public InvertedIndexReducer() {
		client = Commons.getDynamoDBObject();
	}


	/**
	 *
	 * @param key the word
	 * @param values the file names
	 * @param output
	 * @param reporter
	 * @throws IOException
	 */
	@Override
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		PutItemRequest itemRequest;

		ArrayList<String> files = new ArrayList<String>();
		ArrayList<Long> occurs = new ArrayList<Long>();
		Map<String, AttributeValue> items;// = new HashMap<String, AttributeValue>();

		makeFilesAndNumberOfOccurrence(values, files, occurs);

		int index = 0;
		StringBuilder result = new StringBuilder();
		result.append(";");
		while (index < files.size()) {
			result.append(files.get(index) + "," + occurs.get(index).toString() + ";");

	        /*Put item into DynamoDB*/
			items = new HashMap<String, AttributeValue>();
			items.put("Key", new AttributeValue().withS(key.toString()));
			items.put("Location", new AttributeValue().withS((files.get(index)).replaceAll("!!", "/")));
			itemRequest = new PutItemRequest().withTableName("InvertedIndex").withItem(items);
			client.putItem(itemRequest);
			index++;
		}

		output.collect(key, new Text(result.toString()));
		
		/*
		 * Output Style:
		 * 
		 * appeared	 ;file3.txt,1;
		 * appetite	 ;file3.txt,2;file3.txt,1;
		 * ...
		 *
		 */
	}

	/**
	 * Retrieve the files and number of occurrence in each of them in
	 * "files" and "occurs" ArrayLists
	 *
	 * @param values
	 * @param occurs
	 * @param files
	 */
	public void makeFilesAndNumberOfOccurrence(Iterator<Text> values, ArrayList<String> files, ArrayList<Long> occurs) {
		while (values.hasNext()) {
			Text Value = values.next();
			//check if exist or not
			Iterator<String> it = files.iterator();
			int index = 0;
			while (it.hasNext()) {
				String filename = it.next();
				if (filename.equals(Value.toString())) {
					long increasedCounter = occurs.get(index).longValue();
					occurs.set(index, new Long(++increasedCounter));
					break;
				}
				index++;
			}
			//not in files
			files.add(Value.toString());
			occurs.add(new Long(1));
		}
	}
}
