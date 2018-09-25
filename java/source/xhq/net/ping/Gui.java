package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Gui
{
	JFrame frame;
	Box back;
	Ipbox[] ipbox=new Ipbox[4];

	public class Ipbox
	{
		Box box;
		JLabel ptg;
		JTextArea results;
		JTextField ip;
		JButton start;
		JButton stop;
		
		public Ipbox(Pinger pinger,String defaultIp,PingerStarter pst){
			box=new Box(BoxLayout.Y_AXIS);
			ptg=new JLabel("0%");
			results=new JTextArea();
			results.setEditable(false);
			results.setSize(350,200);
			ip=new JTextField(defaultIp);
			ip.setPreferredSize(new Dimension(350,20));
			start=new JButton("开始");
			stop=new JButton("停止");
			stop.setEnabled(false);

			JScrollPane jsp=new JScrollPane(results);
			jsp.setWheelScrollingEnabled(false);
			jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			jsp.setPreferredSize(new Dimension(350,200));
			Box ipup=new Box(BoxLayout.X_AXIS);
			Box ipmid=new Box(BoxLayout.X_AXIS);
			Box ipdown=new Box(BoxLayout.X_AXIS);
			ipup.setPreferredSize(new Dimension(350,20));
			ipmid.setPreferredSize(new Dimension(350,20));
			ipdown.setPreferredSize(new Dimension(350,30));

			start.addActionListener(pinger.new StartListener());
			stop.addActionListener(pinger.new StopListener());
		
			ipup.add(new JLabel("IP:"));
			ipup.add(ip);
			ipmid.add(new JLabel("丢包率："));
			ipmid.add(ptg);
			ipdown.add(start);
			ipdown.add(stop);
			box.add(ipup);
			box.add(ipmid);
			box.add(ipdown);
			box.add(jsp);
			back.add(box);
			pinger.start=start;
			pinger.stop=stop;
			pinger.ptgout=ptg;
			pinger.results=results;
			pinger.ipin=ip;
			pinger.pst=pst;
		}//create
	}//endclass

	public Gui(){
		frame=new JFrame("Ping");
		back=new Box(BoxLayout.X_AXIS);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,710,300);
	}
}