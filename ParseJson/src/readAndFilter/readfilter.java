package readAndFilter;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

public class readfilter {
	static List<String> contents = new ArrayList<String>();
	static int fileCount = 0;
	static int progressCount = 0;
	static int total = 0;
	
    public static void main(String [] args) {
    		read();	
    }
    
    private static void read(){
    	
    	File folder = new File("D:/CS5344/archiveteam-twitter-stream-2015-04/extracted");
    	File[] listOfFiles = folder.listFiles();
    	total = listOfFiles.length;

    	for (File file:listOfFiles){
    		progressCount = progressCount + 1;
    		readEachFile(file);
    	}

    }
    
    private static void readEachFile(File file){
    	
    	try (Stream<String> lines = Files.lines (file.toPath(), StandardCharsets.UTF_8))
    	{
    	    for (String line : (Iterable<String>) lines::iterator)
    	    {
              // process the line.
           	if (line.charAt(2) == 'd') //igone if it is delete
           		continue;
           	else{
                System.out.println(
                        "Progress: '" + 
                        file.getName() + "' " + progressCount + "/" + total); 
           		filterAndWrite(line);
           	}
    	    }
    	}
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                file.getName() + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + file.getName() + "'");                  
        }
    }
    private static void filterAndWrite(String line) {
		// TODO Auto-generated method stub
		
    	try {
    		JSONObject json = new JSONObject(line);
    		String text = json.getString("text");
    		String created = json.getString("created_at");
    		String timeStamp = json.getString("timestamp_ms");
    		int retweet = json.getInt("retweet_count");
    		int favorite = json.getInt("favorite_count");

    		JSONObject output = new JSONObject();
    		output.put("text", text);
    		output.put("created_at", created);
    		output.put("timestamp_ms", timeStamp);
    		output.put("retweet_count", retweet);
    		output.put("favorite_count", favorite);
    		
    		String outputString = output.toString();
    		contents.add(outputString);
    		if (contents.size() > 500000){
    			fileCount = fileCount+1;
    			useByfferedFileWriter(contents, "D:/CS5344/" + fileCount + ".txt");
    			contents.clear();
    		}
    		
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    	}


	}
    
    /**
	 * Write raw data to a big file - use BufferedOutputStream
	 */
	public static void useBufferedOutPutStream(List<String> content,String filePath) {
		BufferedOutputStream bout = null;
		try {

			
			bout = new BufferedOutputStream( new FileOutputStream(filePath) );

			for (String line : content) {
				line += System.getProperty("line.separator");
				bout.write(line.getBytes());
			}

		} catch (IOException e) {

		} finally {

			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e) {

				}
			}
		}

	}
	
	/**
	 * Write a big list of Strings to a file - Use a BufferedWriter
	 */
	public static void useByfferedFileWriter(List<String> content,
			String filePath) {

		File file = new File(filePath);
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			// Write the lines one by one
			for (String line : content) {
				line += System.getProperty("line.separator");
				bufferedWriter.write(line);

				// alternatively add bufferedWriter.newLine() to change line
			}

		} catch (IOException e) {
			System.err.println("Error writing the file : ");
			e.printStackTrace();
		} finally {

			if (bufferedWriter != null && fileWriter != null) {
				try {
					bufferedWriter.close();
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

    }

