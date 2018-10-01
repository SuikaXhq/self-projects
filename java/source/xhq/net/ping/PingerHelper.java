package xhq.net.ping;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PingerHelper
{
	Gui gui;
	Pinger pinger;
	ConfigManager manager;
	DecimalFormat df=new DecimalFormat("0.00%");
	

	public static void main(String[] args) 
	{
		new PingerHelper().initialize();
	}

	public void initialize() {
		manager = new ConfigManager(this.getClass().getProtectionDomain().getCodeSource().getLocation());
	}

	//ÕìÌýÆ÷
	public class StartListener implements ActionListener
	{
		private final int index_;

		public StartListener(int i) {
			index_ = i;
		}

		public void actionPerformed(ActionEvent a){
			results.setText("");
			pinger.startPing();
			gui.start.setEnabled(false);
			gui.stop.setEnabled(true);
		}
	}

	public class StopListener implements ActionListener
	{
		private final int index_;

		public StopListener(int i) {
			index_ = i;
		}

		public void actionPerformed(ActionEvent a){
			pinger.stopPing();
			gui.stop.setEnabled(false);
			gui.start.setEnabled(true);
		}
	}
}
