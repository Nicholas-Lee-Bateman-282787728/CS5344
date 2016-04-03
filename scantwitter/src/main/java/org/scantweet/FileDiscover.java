/***
 * @author jianmin
 * @date 2016 march 05
 * @use running in ubuntu
 * 		input folder structure /home/hadoop/input/month/date[01-31]/hour[00-23]/minutes[00-59].json.bz2
 *		  		
 */
package org.scantweet;

import java.io.File;
import java.util.LinkedList;

public class FileDiscover {
	protected LinkedList<String> ls = new LinkedList<String>();
	//default
	String inputFolderPath = "/media/hadoop/JIANMIN/input";
 	/**
	 * 
	 * @param inputFolderPath  /home/hadoop/input/
	 */
	public FileDiscover(String inputFolderPath){
			this.inputFolderPath = inputFolderPath;
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
	
	public LinkedList<String> getInputFiles(){
		return (this.getListFiles(this.getInputFolder()));
	}
	//get input folder structure
	private LinkedList<String> getListFiles (String folderPath){
		File inFolder = new File(folderPath);
		File[] listOfFiles = inFolder.listFiles();
		for (File file : listOfFiles) {
		    if (file.isDirectory()){
		    	getListFiles(file.getAbsolutePath());
		    }
		    else {
		    	ls.push(file.getAbsolutePath());
		    }
		}
		return ls;
	}

}
