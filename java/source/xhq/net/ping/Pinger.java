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
	Double ptg;
	Double send;
	Double receive;
	JButton start;
	JButton stop;
	JLabel ptgout;
	String ip;
	JTextField ipin;
	JTextArea results;
	Runtime rt;
	BufferedReader in;
	String command;
	String result;
	java.util.Timer timer=new java.util.Timer();
	TimerTask timerTask;
	static Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
	Matcher matcher;
	DecimalFormat df=new DecimalFormat("0.00%");
	PingerStarter pst;

	public Pinger(){
		ptg=0.0;
		send=0.0;
		receive=0.0;
	}

	public void startPing(){
		results.setText("");
		send=0.0;
		receive=0.0;
		ptg=0.0;
		ip=ipin.getText();
		pst.saveIp(this);
		command="ping "+ip+" -n 1";
		rt=Runtime.getRuntime();
		timerTask=new TimerTask() {
            public void run() {
				result=ping();
				results.append(result+"\r\n");
				results.setCaretPosition(results.getText().length());
				send++;
				if (send!=0)
				{
					ptg=(send-receive)/send;
					ptgout.setText(df.format(ptg));
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask,0,1000);
	}

	public String ping(){
		String str=null;
		in = null;  
        rt = Runtime.getRuntime();
		try{
			Process p = rt.exec(command);
			if (p==null)
			{
				return "´íÎó001";
			}else{
				in=new BufferedReader(new InputStreamReader(p.getInputStream()));
				str=in.readLine();
				while(str!=null){
					matcher = pattern.matcher(str);
					while (matcher.find()) {
						receive++;
						return str;
					}
					str=in.readLine();
				}
			}//endif
		}catch(Exception e){
			e.printStackTrace();
		}
		return "³¬Ê±";
	}

	public void stopPing(){
		timerTask.cancel();
		timer.purge();
	}

	public class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a){
			startPing();
			start.setEnabled(false);
			stop.setEnabled(true);
		}
	}

	public class StopListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a){
			stopPing();
			stop.setEnabled(false);
			start.setEnabled(true);
		}
	}
}
