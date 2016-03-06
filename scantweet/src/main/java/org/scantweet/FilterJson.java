package org.scantweet;

import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;

public  class FilterJson {
	 
	private static final String [] KEYWORDS = {
		 "fast 7", "fast7","fast seven","fastseven",
		 "furious 7", "furious7","furious seven","furiousseven",
		 "#ff7","fastandfurious","paulwalker", "paul walker"
	};
	 
	//must be static, why?
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
	
	public static void RunScanTask(JavaSparkContext sc, String input, String output){
		try{
		    JavaRDD<String> inputData = sc.textFile(input).cache();
		    JavaRDD<String> outData = inputData.filter(KEYWORDS_MATCH);
		    long count = outData.count();
		    if (count >  0){
		    	outData.saveAsTextFile(output);
		    }
		    //System.out.println("Lines with keywords: " + outData);
		    			
		}catch(Exception e){
			System.out.println("Spark running error:");
			System.out.println(e.getMessage());
		}
	}
}
