package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class PingerHelper {

	private ConfigManager manager_;
	private ArrayList<IPBox> iPBoxs_ = new ArrayList<IPBox>();
	private ArrayList<String> iPs_;
	private JFrame frame_;// 主框架
	private Box backBox_;// 主背景

	// 菜单
	private JMenuBar menuBar_ = new JMenuBar();
	private JMenu editMenu_ = new JMenu("编辑");
	private JMenuItem plusOption_ = new JMenuItem("添加");

	public static void main(String[] args) {
		try {
			new PingerHelper().initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 构造函数（GUI）
	public PingerHelper() {
		frame_ = new JFrame("Ping");
		backBox_ = new Box(BoxLayout.X_AXIS);
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		plusOption_.addActionListener(new PlusListener());
		editMenu_.add(plusOption_);
		menuBar_.add(editMenu_);
		frame_.add(backBox_);
		frame_.setJMenuBar(menuBar_);
	}

	// 初始化（IPBox）
	public void initialize() {
		try {
			manager_ = new ConfigManager();
			iPs_ = manager_.readAll();
			for (String ip : iPs_) {
				addIPBox_(ip);
			}
			iPBoxs_.get(0).setDeleteButtonDisabled();
			frame_.setBounds(150, 150, 18 + 350 * iPs_.size(), 320);
			frame_.setResizable(false);
			frame_.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加IPBox
	private void addIPBox_(String ip) {
		IPBox tempBox = new IPBox(ip);
		iPBoxs_.add(tempBox);
		tempBox.addStartListener(new StartListener(iPBoxs_.size() - 1));
		tempBox.addStopListener(new StopListener(iPBoxs_.size() - 1));
		tempBox.addDeleteListener(new DeleteListener(iPBoxs_.size() - 1));
		backBox_.add(tempBox.getBox());
	}

	// 保存IP
	private void saveIP_() {
		try {
			manager_.write(iPs_);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//侦听器
	public class StartListener implements ActionListener {
		private int index_;

		public StartListener(int i) {
			index_ = i;
		}

		public void shift() {
			--index_;
		}

		public void actionPerformed(ActionEvent a) {
			if (manager_.isIP(iPBoxs_.get(index_).getIP())) {
				if (iPBoxs_.get(index_).isIPFieldChanged()) {
					iPBoxs_.get(index_).loadIP();
					iPs_.set(index_, iPBoxs_.get(index_).getIP());
					saveIP_();
				}
				iPBoxs_.get(index_).startPing();
			} else {
				JOptionPane.showMessageDialog(null, "输入值非IP!", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class StopListener implements ActionListener {
		private int index_;

		public StopListener(int i) {
			index_ = i;
		}

		public void shift() {
			--index_;
		}

		public void actionPerformed(ActionEvent a) {
			iPBoxs_.get(index_).stopPing();
		}
	}

	public class PlusListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			addIPBox_("");
			iPs_.add("");
			frame_.setSize(18 + 350 * iPs_.size(), (int) frame_.getSize().getHeight());
			frame_.validate();
			frame_.repaint();
		}
	}

	public class DeleteListener implements ActionListener {
		private int index_;

		public DeleteListener(int i) {
			index_ = i;
		}

		public void shift() {
			--index_;
		}

		public void actionPerformed(ActionEvent a) {
			iPBoxs_.get(index_).close();
			backBox_.remove(iPBoxs_.get(index_).getBox());
			iPBoxs_.remove(index_);
			iPs_.remove(index_);
			frame_.setSize(18 + 350 * iPs_.size(), (int) frame_.getSize().getHeight());
			frame_.validate();
			for (int i = index_; i < iPBoxs_.size(); ++i) {
				((StartListener) iPBoxs_.get(i).getStartListener()).shift();
				((StopListener) iPBoxs_.get(i).getStopListener()).shift();
				((DeleteListener) iPBoxs_.get(i).getDeleteListener()).shift();
			}
			saveIP_();
		}
	}
}
