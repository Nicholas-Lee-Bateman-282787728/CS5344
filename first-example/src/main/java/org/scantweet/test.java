/***
 * @author jianmin
 * @date 2016 march 05
 */
package org.scantweet;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.scantweet.FolderBuilder;
public class test {
	public static void main(String[] args) {
		String ip = "/home/hadoop/input/";
		FolderBuilder fb = new FolderBuilder(ip);
		HashMap<String, LinkedList<String>> ippaths = fb.getInputPath();
		if(ippaths != null){
				LinkedList<String> inputs = ippaths.get(FolderBuilder.IPKEY);
				SparkConf conf = new SparkConf().setAppName("Scan Tweets in JSON")
						.setMaster("local");
				JavaSparkContext sc = new JavaSparkContext(conf);
				int size = inputs.size();
				for(int i=0; i<size;++i){
					//System.out.println(inputs.get(i).toString());
					//build jobs
					String input = inputs.get(i);
					processJson(input, sc);
				}
				
				sc.close();
		}
	}
	//each date folder has 24 folders 
	private static void processJson(String input, JavaSparkContext sc){
		File inFolder = new File(input);
		//get input folder structure
		File[] listOfFiles = inFolder.listFiles();
		for (File file : listOfFiles) {
			//get get all hours folder
		    if (file.isDirectory()){
		    	//System.out.println(file.getAbsolutePath());
		    	String infile = file.getAbsolutePath();
		    	String outfile = infile.replace("input", "output");
		    	FilterJson fj = new FilterJson(infile, outfile);
				fj.RunJob(sc);
		    }
		}
	}
}
