package xhq.net.ping;

import javax.swing.*;

public class Gui
{
	JFrame frame;
	Box back;
	ArrayList<Ipbox> ipboxes=new ArrayList<Ipbox>();
	
	//¼Ó¼õIpbox¹¦ÄÜ
	JMenuBar menuBar;
	JMenu edit;
	JMenuItem plus;
	JMenuItem minus;

	public class Ipbox
	{
		

		
	}//end inner class

	public Gui(){
		frame=new JFrame("Ping");
		back=new Box(BoxLayout.X_AXIS);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(150,150,710,300);
	}
}