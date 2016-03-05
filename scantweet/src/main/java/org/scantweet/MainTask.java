/***
 * @author jianmin
 * @date 2016 march 05
 * @use running in ubuntu
 * 		Input folder structure /home/hadoop/input/month/date[01-31]/hour[00-23]/minutes[00-59].json.bz2
 *		Change keywords in FilterJson class
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
				.setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		String ip = "/home/hadoop/input/";
		FileDiscover fd = new FileDiscover(ip);
		LinkedList<String> ippaths = fd.getInputFiles();
		for(String input:ippaths){
			processJson(input, sc);
		}
			sc.close();
	}
	//each date folder has 24 folders 
	private static void processJson(String input, JavaSparkContext sc){
		    	String output = input.replace("input", "output");
		    	FilterJson fj = new FilterJson(input, output);
				fj.RunScanTask(sc);
	}
}