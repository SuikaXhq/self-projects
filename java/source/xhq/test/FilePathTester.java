package xhq.test;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

public class FilePathTester
{
	public static void main(String[] args) 
	{
		try
		{
//			File file = new File("../resource/ping/DefaultIp.txt");
//			System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
			new FilePathTester().go();
//			System.out.println(file.getCanonicalPath());
//			System.out.println(file.isFile());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

	public void go() throws Exception {
		System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation());
		BufferedReader bfr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/DefaultIp.txt")));
		System.out.println(bfr.readLine());
		bfr.close();
		JarOutputStream jarStream = new JarOutputStream(new FileOutputStream(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()));
		JarEntry txt = new JarEntry("DefaultIp.txt");
		jarStream.putNextEntry(txt);
		BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(jarStream));
		bfw.write("123456");
		bfw.close();
	}
}