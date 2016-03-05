/***
 * @author jianmin
 * @date 2016 march 05
 * @use running in ubuntu
 * 		input folder structure /home/hadoop/input/month/date[01-31]/hour[00-23]/minutes[00-59].json.bz2
 *		  		
 */
package org.scantweet;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.scantweet.FileDiscover;
public class testFolder {
	 
	public static void main(String[] args) {
		String ip = "/home/hadoop/input/";
		FileDiscover fb = new FileDiscover(ip);
	    LinkedList<String> ippaths = fb.getInputFiles();
	    for (String input: ippaths){
	    	System.out.println(input);
	    } 
	}
}
