/**
 * @author Emad Heydari Beni
 * http://heydari.be
 */

package be.heydari.linkgraph;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import java.io.IOException;

public class LinkGraphDriver {

	/**
	 * @param args input and output file location (starting with hdfs://localhost for HDFS locations)
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException {
		JobClient client = new JobClient();
		JobConf conf = new JobConf(LinkGraphDriver.class);

		conf.setJobName("LinkGraph");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		conf.setMapperClass(LinkGraphMapper.class);
		conf.setReducerClass(LinkGraphReducer.class);


		client.setConf(conf);

		try {
			JobClient.runJob(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
