besearchpr
==========

## Inverted Index
This is a Map/Reduce Hadoop job in order to analyze a set of web pages downloaded in an Amazon S3 bucket, and generates the Inverted Indexes. The output presents all words with number of occurence in each web page. Somthing like follows:

#### How to Run on it on Amazon EMR
- Export the JAR file.
- Go to Amazon EMR console.
- Create a cluster.
- Select your logs path (must be created beforehand): s3://besearchhadoop/invertedindex/logs/
- Select your KeyPair for future purposes.
- Select your preferred instance types with proper number of them.
- In "steps" section, select the "Custom JAR", and then your JAR file which is located in the S3: s3://besearchhadoop/invertedindex/besearchpr.jar
- Then, for the "Arguments" of your jar, add followings (delimited by space): be.heydari.invertedindex.InvertedIndexDriver s3n://besearchdonebucket s3n://besearchhadoop/invertedindex/output

Remarks: The "output" folder does not exist at the beginning. The Hadoop job will create it for you.
