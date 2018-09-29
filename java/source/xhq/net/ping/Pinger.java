package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

public class Pinger 
{
	public void static Pattern PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
	String ip;
	Runtime rt = Runtime.getRuntime();
	BufferedReader in;
	String command;
	String result;
	String temp = null;
	Matcher matcher;
	DecimalFormat df=new DecimalFormat("0.00%");

	public Pinger(){
	}

	public void startPing(){
		ip=ipin.getText();
		pst.saveIp(this);
		command="ping "+ip+" -n 1";
		timer.scheduleAtFixedRate(timerTask,0,1000);
	}

	public String ping(){
		in = null;
		try{
			Process process = rt.exec(command);
			if (process == null) {
				return "´íÎó001";
			} else {
				in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				temp = in.readLine();
				while(temp != null){
					matcher = PATTERN.matcher(temp);
					while (matcher.find()) {
						receive++;
						return temp;
					}
					temp = in.readLine();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "³¬Ê±";
	}

	public void stopPing(){
		timerTask.cancel();
		timer.purge();
	}

	
}
