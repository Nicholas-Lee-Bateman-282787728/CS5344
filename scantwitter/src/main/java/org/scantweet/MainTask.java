/***
 * @author jianmin
 * @date 2016 march 05
 * @use running in ubuntu
 * 		Input folder structure
 * 		/home/hadoop/input/month/date[01-31]/hour[00-23]/minutes[00-59].json.bz2
 *		Change keywords in FilterJson class
 *	    Modify file paths if necessary
 */
package org.scantweet;

import java.util.LinkedList;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.scantweet.FileDiscover;

public class MainTask {
	
	public static void main(String[] args) {
		//update local[2] to increase threads
		SparkConf conf = new SparkConf().setAppName("Scan Tweets in JSON")
				.setMaster("local[3]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		String ip = "/media/hadoop/JIANMIN/input/";
		FileDiscover fd = new FileDiscover(ip);
		LinkedList<String> ippaths = fd.getInputFiles();
		for(String input:ippaths){
			processJson(input, sc);
		}
			sc.close();
	}
	//each month has date folder, each date folder has 24 folders
	//each hour has 60 folders
	private static void processJson(String input, JavaSparkContext sc){
				//mapping output path to input
		    	String output = input.replace("input", "output");
		    	FilterJson.RunScanTask(sc, input, output);
	}
}