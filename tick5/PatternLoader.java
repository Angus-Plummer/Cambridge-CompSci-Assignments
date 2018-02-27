package uk.ac.cam.ap801.tick5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class PatternLoader {
	
	public static List<Pattern> load(Reader r) throws IOException {
		BufferedReader buff = new BufferedReader(r);
		List<Pattern> resultList = new LinkedList<Pattern>();
		boolean endOfStream = false;
		while (endOfStream == false) { //will stop running when the end of the stream has been reached.
			try{
				String newPattern = buff.readLine();
				if (!(newPattern == null)) {   //checks if the end of the stream has been reached and null pointers are being returned.
					Pattern p = new Pattern(newPattern);
					resultList.add(p);
				}
				else {
					endOfStream = true;
				}
			} catch (PatternFormatException formatError){
					System.out.println(formatError.getDescription());
			} catch (IOException streamError){
					System.out.println("Error: A problem occured within the stream");
			}
		}
		return resultList;
	}
	
	public static List<Pattern> loadFromURL(String url) throws IOException {
		 URL destination = new URL(url);
		 URLConnection conn = destination.openConnection();
		 return load(new InputStreamReader(conn.getInputStream()));
		}
	
		public static List<Pattern> loadFromDisk(String filename) throws IOException {
		 return load(new FileReader(filename));
		}

} 
