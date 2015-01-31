/**
 * @author Emad Heydari Beni
 * http://heydari.be
 */

package be.heydari.linkgraph;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LinkGraphMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	private final static Text location = new Text();
	private final static Text outgoing_Text = new Text();
	private final static Text incoming_Text = new Text();
	public HTMLLinkExtractor linkExtractor = new HTMLLinkExtractor();

	public LinkGraphMapper() {
	}

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		String line = value.toString();

		FileSplit filesplit = (FileSplit) reporter.getInputSplit();
		String filename = filesplit.getPath().getName();
		filename = filename.replaceAll("!!", "/");
		location.set(filename);

		Vector<HTMLLinkExtractor.HtmlLink> links = linkExtractor.grabHTMLLinks(line);
		List<URL> absLinks = convert_relativeToAbsoluteURL(filename, links);

		StringBuilder outgoing = new StringBuilder();
		outgoing.append("out,");
		StringBuilder incoming = new StringBuilder();
		incoming.append("in,");

		for (URL link : absLinks) {
			//outgoing
			outgoing.append(link.toString());
			outgoing_Text.set(outgoing.toString());
			output.collect(location, outgoing_Text);

			//outgoing
			incoming.append(location);
			incoming_Text.set(link.toString());
			output.collect(incoming_Text, new Text(incoming.toString()));
		}
	}


	/**
	 * Convert relative links to absolute links
	 * 
	 * @param baseLink
	 * @param relativeLinks
	 * @return
	 */
	public ArrayList<URL> convert_relativeToAbsoluteURL(String baseLink, Vector<HTMLLinkExtractor.HtmlLink> relativeLinks) {
		ArrayList<URL> absLinks = new ArrayList<URL>();
		
		for (HTMLLinkExtractor.HtmlLink htmlLink : relativeLinks) {
			URL absoluteURL;
			try {
				absoluteURL = new URL(new URL(baseLink), htmlLink.getLink());
				absLinks.add(absoluteURL);
			} catch (MalformedURLException e) {
				// ignore
			}
		}

		return absLinks;
	}

}
