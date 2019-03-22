package xhq.test;

import java.io.*;

public class BufferedReaderTester {

	public static void main(String[] args) {
		try {
			BufferedReaderTester t = new BufferedReaderTester();
			String classPath = t.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			classPath = classPath.substring(1,classPath.lastIndexOf('/'));
			String filePath = classPath + "/123.txt";
			File file = new File(filePath);
			System.out.println(file.isFile());
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String temp = null;
			while ((temp = bfr.readLine())!=null)System.out.println(temp);
			bfr.close();
		} catch (Exception e) {
			
		}
		
	}

}
