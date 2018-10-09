package xhq.net.ping;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PingerHelper
{
	public final static DecimalFormat FORMAT=new DecimalFormat("0.00%");
	private Pinger pinger_;
	private ConfigManager manager_;
	private ArrayList<IPBox> iPBoxs_ = new ArrayList<IPBox>();
	private ArrayList<String> iPs_;
	private JFrame frame_;//主框架
	private Box backBox_;//主背景

	//菜单
	private JMenuBar menuBar_;
	private JMenu editMenu_ = new JMenu("编辑");
	private JMenuItem plusOption_ = new JMenuItem("添加一个");
	private JMenuItem minusOption_ = new JMenuItem("删除一个");

	public static void main(String[] args) {
		new PingerHelper().initialize();
	}

	//构造函数（GUI）
	public PingerHelper() {
		frame_ = new JFrame("Ping");
		backBox_ = new Box(BoxLayout.X_AXIS);
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		plusOption_.addActionListener(new PlusListener());
		minusOption_.addActionListener(new MinusListener());
		editMenu_.add(plusOption_);
		editMenu_.add(minusOption_);
		menuBar_.add(editMenu_);
		frame_.add(backBox_);
		frame_.add(menuBar_);
	}

	//初始化（IPBox）
	public void initialize() {
		try {
			pinger_ = new Pinger();
			manager_ = new ConfigManager();
			iPs_ = manager.read();
			IPBox tempBox = null;
			for (int i = 0;i < iPs_.size();i++) {
				tempBox = new IPBox(ip);
				iPBoxs_.add(tempBox);
				tempBox.addStartListener(new StartListener(i));
				tempBox.addStopListener(new StopListener(i));
				backBox_.add(tempBox);
			}
			frame_.setBounds(150,150,400 * iPs_.size(),300);
			frame_.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//侦听器
	public class StartListener implements ActionListener
	{
		private final int index_;

		public StartListener(int i) {
			index_ = i;
		}

		public void actionPerformed(ActionEvent a) {
/*			results.setText("");
			pinger.startPing();
			gui.start.setEnabled(false);
			gui.stop.setEnabled(true);
*/		}
	}
	public class StopListener implements ActionListener
	{
		private final int index_;

		public StopListener(int i) {
			index_ = i;
		}

		public void actionPerformed(ActionEvent a) {
/*			pinger.stopPing();
			gui.stop.setEnabled(false);
			gui.start.setEnabled(true);
*/		}
	}
	public class PlusListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a) {
		}
	}
	public class MinusListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a) {
		}
	}
}
