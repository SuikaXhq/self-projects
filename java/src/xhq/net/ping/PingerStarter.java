package xhq.net.ping;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PingerStarter
{
	Gui gui;
	Pinger[] pinger=new Pinger[5];
	String[] defaultIps={"","","",""};
	String[] ip={"","106.184.3.6","210.140.44.104"};
	File ipFile;
	public static void main(String[] args) 
	{
		PingerStarter pst=new PingerStarter();
		pst.initialize();
	}

	public void initialize(){
		int i=1;
		try{
			ipFile = new File("DefaultIp.txt");
			BufferedReader bfr=new BufferedReader(new FileReader(ipFile));
			String s=null;
			i=1;
			while ((s=bfr.readLine())!=null)
			{
				defaultIps[i]=s;
				i++;
			}
			for (i=0;i<=2;i++)
			{
				if (defaultIps[i].equals(""))
				{
					defaultIps[i]=ip[i];
				}
			}
			bfr.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		gui=new Gui();
		for (i=1;i<=defaultIps.length;i++)
		{
			if (!(defaultIps[i].equals("")))
			{	
				pinger[i]=new Pinger();
				gui.ipbox[i]=gui.new Ipbox(pinger[i],defaultIps[i],this);
				if (i!=defaultIps.length)
				{
					gui.back.add(Box.createHorizontalStrut(10));
				}
			}else{
				break;
			}
		}
		gui.frame.add(gui.back);
		gui.frame.setVisible(true);
	}

	public void saveIp(Pinger per){
		int i=0;
		String ipFinal="";
		for (int j=0;j<=5;j++)
		{
			if (pinger[j]==per)
			{
				i=j;
				break;
			}
		}
		try{
			BufferedReader bfr=new BufferedReader(new FileReader(ipFile));
			String rd=null;
			int c=1;
			while (true)
			{
				if (((rd=bfr.readLine())==null)&c<=i)
				{
					String lines=null;
					for (int k=1;k<=i-c;k++)
					{
						lines=lines+"\r\n";
					}
					ipFinal=ipFinal+lines+per.ip;
					break;
				}else{
					if (rd==null)
					{
						break;
					}else{
						if (c==i)
						{
							ipFinal=ipFinal+per.ip+"\r\n";
						}else{
							ipFinal=ipFinal+rd+"\r\n";
						}
					}
				}
				c++;
			}
			bfr.close();
			BufferedWriter bfw=new BufferedWriter(new FileWriter(ipFile));
			bfw.write(ipFinal,0,ipFinal.length());
			bfw.flush();
			bfw.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
