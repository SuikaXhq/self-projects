package xhq.net.ping;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PingerHelper
{
	
	private ConfigManager manager_;
	private ArrayList<IPBox> iPBoxs_ = new ArrayList<IPBox>();
	private ArrayList<String> iPs_;
	private JFrame frame_;//�����
	private Box backBox_;//������

	//�˵�
	private JMenuBar menuBar_;
	private JMenu editMenu_ = new JMenu("�༭");
	private JMenuItem plusOption_ = new JMenuItem("���һ��");
	private JMenuItem minusOption_ = new JMenuItem("ɾ��һ��");

	public static void main(String[] args) {
		new PingerHelper().initialize();
	}

	//���캯����GUI��
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

	//��ʼ����IPBox��
	public void initialize() {
		try {
			pinger_ = new Pinger();
			manager_ = new ConfigManager();
			iPs_ = manager.readAll();
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

	//����IP
	public void saveIP(int index) {

	}

	//������
	public class StartListener implements ActionListener
	{
		private final int INDEX_;

		public StartListener(int i) {
			INDEX_ = i;
		}

		public void actionPerformed(ActionEvent a) {
			if (!iPBoxs_.get(INDEX_).isIPFieldChanged()) {
				saveIP(INDEX_);
				iPBoxs_.get(INDEX_).loadIP();
				iPBoxs_.get(INDEX_).turnOff();
			}
			iPBoxs_.get(INDEX_).startPing();
		}
	}
	public class StopListener implements ActionListener
	{
		private final int index_;

		public StopListener(int i) {
			INDEX_ = i;
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
