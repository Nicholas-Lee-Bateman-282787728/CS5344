package org.scantweet;
import java.io.File;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;

public class FilterJson {
	String input = "";
	String output = "";
	
	public FilterJson(String input, String output){
		this.input = input;
		this.output = output;
	}
	
	public String getInput(){
		return this.input;
	}
	public void setInput(String input){
		this.input = input;
	}
	
	public String getOutput(){
		return this.output;
	}
	public void setOutput(String output){
		this.output = output;
	}
	
	private static final String [] KEYWORDS = {
		 "fast 7", "fast7","fast seven","fastseven",
		 "furious 7", "furious7","furious seven","furiousseven",
		 "ff7","fastandfurious","paulwalker", "paul walker"
	};
	
	private static final Function<String, Boolean> KEYWORDS_MATCH = 
		new Function<String, Boolean>(){
			private static final long serialVersionUID = 1L;
			public Boolean call(String s) throws Exception {
	          return searchKeyWord(s);
	        }
	};
	
	private static Boolean searchKeyWord(String s){
		Boolean empty = (s == null) || (s.trim().length() < 1);
		if(empty){
			return empty;
		}
		for(String word :KEYWORDS){
			if(s.toLowerCase().contains(word)){
				return true;
			}
		}
		
		return false;
	}
	
	public void RunJob(JavaSparkContext sc){
		try{
			String inpuFile = this.getInput();
		    String outputFile = this.getOutput();
		    JavaRDD<String> inputData = sc.textFile(inpuFile).cache();
		    JavaRDD<String> outData = inputData.filter(KEYWORDS_MATCH);
		    outData.saveAsTextFile(outputFile);
		    //System.out.println("Lines with keywords: " + outData);
		    			
		}catch(Exception e){
			System.out.println("Simple error.");
			System.out.println(e.getMessage());
		}
	}
	
	/*public static void main(String[] args) {
		
		String inpuFile = "/home/hadoop/test";
	    String outputFile = "/home/hadoop/out";
		SparkConf conf = new SparkConf().setAppName("Simple Application")
	    								.setMaster("local");
	    JavaSparkContext sc = new JavaSparkContext(conf);
	    JavaRDD<String> inputData = sc.textFile(inpuFile).cache();
	    	
	    JavaRDD<String> outData = inputData.filter(KEYWORDS_MATCH);
	  
	    outData.collect();
	    
	    outData.saveAsTextFile(outputFile);
	  
	    
	    //System.out.println("Lines with keywords: " + outData);
	    
	    sc.close();
		
	}
	*/
	
		
	
	
}
