
package be.heydari.invertedindex;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;


/**
 * Driver class containing the main method
 * @author Emad Heydari Beni
 */
public class InvertedIndexDriver {

	/**
	 * @param args input and output file location
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException {
		JobClient client = new JobClient();
		JobConf conf = new JobConf(InvertedIndexDriver.class);
		
		conf.setJobName("InvertedIndex");
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		conf.setMapperClass(InvertedIndexMapper.class);
		conf.setReducerClass(InvertedIndexReducer.class);
		
		client.setConf(conf);
	    try {
	      JobClient.runJob(conf);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

}
