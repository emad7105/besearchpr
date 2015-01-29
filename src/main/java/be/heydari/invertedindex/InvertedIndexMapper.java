/**
 * @author Emad Heydari Beni
 * From: http://hadoop.apache.org/common/docs/r1.0.3/mapred_tutorial.html
 */

package be.heydari.invertedindex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class InvertedIndexMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	private final static Text location = new Text();
	private final static Text word = new Text();

	public String stopWordsString = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your";
	public List<String> stopwords;


	public InvertedIndexMapper() {
		//Stop Words (preparing) 
		StringTokenizer stopwordsTokenizer = new StringTokenizer(stopWordsString, ",");
		stopwords = new ArrayList<String>();
		while(stopwordsTokenizer.hasMoreTokens()) {
			stopwords.add(stopwordsTokenizer.nextToken());
		}
	}

	/**
	 * Map method for word count
	 */
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		String line = value.toString();

		line = line.toLowerCase();
		line = removePunctuations(line);


		FileSplit filesplit = (FileSplit) reporter.getInputSplit();
		String filename = filesplit.getPath().getName();
		location.set(filename);


		StringTokenizer tokenizer = new StringTokenizer(line);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (!isStopWord(token) && !isNumeric(token) && (token.length()>3)) {
				word.set(token);
				output.collect(word, location);
			}
		}
	}

	/**
	 * remove punctuation, and replacing them with space.
	 * ("-" and "'" are removed as well)
	 *
	 * @param str input string
	 * @return String without punctuation
	 */
	public String removePunctuations(String str) {
		str = str.replaceAll("\\p{Punct}", " ");
		return str;
	}

	public boolean isStopWord(String word) {
		return stopwords.contains(word);
	}

	public boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(.\\d+)?");
	}
}
