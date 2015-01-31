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
- Then, for the "Arguments" of your jar, add followings (delimited by space): be.heydari.invertedindex.InvertedIndexDriver s3n://besearchdonebucket/ s3n://besearchhadoop/invertedindex/output

#### Remarks 
- The "output" folder does not exist at the beginning. The Hadoop job will create it for you.
- The s3n://besearchdonebucket/ must have the "/" at the end. Because Hadoop file system cannot detect the bucket name as a valid storage. If you want to be sure, you can conncet to your Master node using SSH and list your bucket with following command.
 
 ```bash
 hadoop fs -ls s3n://besearchdonebucket/
 ```
 
 ## Link Graph
 This Hadoop job is about figuring out and producing the in-comming and out-going links of all links inside all pre-downloaded pages in our distributed file storage system (Amazon S3). In the end the Reduce step generates:
 
 ```bash
 		# output style:
		 
		 # LINK,<#,incoming>,<#,outgoing>
		 
		 ink1,<10,link2,link3,...>,<4,link2,link5,link6,...>
		 link2,<4,link3,link1,...>,<3,link1,link3,...>
		 ...
 ```
 

# Author
Emad Heydari Beni <br>
http://heydari.be <br>
Follow me Twitter @heydari_be
