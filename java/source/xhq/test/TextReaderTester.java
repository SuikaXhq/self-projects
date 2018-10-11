package xhq.test;

import java.io.*;

public class TextReaderTester 
{
	public static void main(String[] args) 
	{
		try//”√matcher∫Õpattern
		{
			File file = new File("TextTest.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String newLine = reader.readLine();
			String[] str = newLine.split(":");
			str[1].substring(3);
			for (String temp : str)
			{
				System.out.print("\"" + temp + "\" ");
			}
			System.out.println("\n123\t456");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
