package xhq.suviet;

import java.io.*;

public class LogPreparer
{
	File file_;
	BufferedReader reader_;
	StringBuffer content_;//LogÄÚÈÝ
	
	public LogPreparer(File file) {
		try {
			file_ = file;
			reader_ = new BufferedReader(new FileReader(file_));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void readAll() throws IOException {
		while ()
	}
	
	public String readLine() throws IOException {
		return reader_.readLine();
	}
}
