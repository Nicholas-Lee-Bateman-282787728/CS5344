/***
 * @author jianmin
 * @date 2016 march 05
 * @use running in ubuntu
 * 		input folder structure /home/hadoop/input/month/date/hour/minutes
 *		  		
 */
package org.scantweet;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class FolderBuilder {
	public static String IPKEY = "ip";
	String inputFolderPath = "/home/hadoop/input/";
 	/**
	 * 
	 * @param inputFolderPath  /home/hadoop/input/
	 */
	public FolderBuilder(String inputFolderPath){
			this.inputFolderPath = inputFolderPath;
	}
	
	public  HashMap<String, LinkedList<String>> getInputPath(){
			return getFolders(this.getInputFolder());
	}
	
	private String getInputFolder(){
		if(this.inputFolderPath.isEmpty()){
			System.out.println("Input or Out Folder path is empty. Use default path.");
		}
		return  this.inputFolderPath;
	}
	
	public void setFolderPath(String inputFolderPath){
		this.inputFolderPath = inputFolderPath;
	}
	
	private  HashMap<String, LinkedList<String>> getFolders (String folderPath){
		HashMap<String, LinkedList<String>> hm = new HashMap<String, LinkedList<String>>();
		File inFolder = new File(folderPath);
		//get input folder structure
		File[] listOfFiles = inFolder.listFiles();
		for (File file : listOfFiles) {
			//get get all dates folder
		    if (file.isDirectory()){
		    	//System.out.println(file.getName());
		    	//System.out.println(file.getAbsolutePath());
		    	String ippath = file.getAbsolutePath();
		    	LinkedList<String> ips = hm.get(IPKEY);
		    	if(ips == null){
			    	LinkedList<String> temp = new LinkedList<String>();
			    	temp.push(ippath);
			    	hm.put(IPKEY, temp);
		    	}else{
		    		ips.push(ippath);
		    	}
		    }
		}
		return hm;
	}
}
