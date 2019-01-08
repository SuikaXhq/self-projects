/**
 * Class SuvietLauncher
 * Version 0.0.3
 * Feature: 输入查找的词，返回次数、消息总数及每个人的详细情况
 * 
 * Ver0.0.3 12/23 Updated:
 * 		- 加入净发言数的返回值
 * 
 * Ver0.0.2 12/21 Updated:
 * 		- 支持切换文件
 * 		- 更新UI
 */

package xhq.suviet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Calendar;

public class SuvietLauncher {
	private LogAnalyzer analyzer_ = new LogAnalyzer();
	private ImageIcon icon_ = new ImageIcon(this.getClass().getResource("res/1.jpg"));
	private ShowFrame frame_ = new ShowFrame(this);
	private JFileChooser fileChooser_ = new JFileChooser(frame_.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	private File file_;
	private DateSettingFrame dsF_ = new DateSettingFrame();
	
	
	public static void main(String[] args) {
		new SuvietLauncher().init();
	}
	
	private void init() {
		//选择读取文件
		fileChooser_.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser_.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
		if (JFileChooser.APPROVE_OPTION == fileChooser_.showOpenDialog(frame_)) {
			file_ = fileChooser_.getSelectedFile();
			nextAnalyze();
		}
	}
	
	private void nextAnalyze() {
		if (file_ == null) {
			init();
			return;
		}
		
		
		try {
			String finding = JOptionPane.showInputDialog("请键入查找的词。");
			if (finding == null || finding.equals(""))return;
			
			long startTime = System.currentTimeMillis();
			analyzer_.reset(file_, finding);
			int[][] result = analyzer_.analyze();
			long stopTime = System.currentTimeMillis();
			
			frame_.clearText();
			if (analyzer_.getMode() == LogAnalyzer.DATE_MODE) {
				String[] dateRange = analyzer_.getDateRange();
				frame_.addText("从" + dateRange[0] + "到" + dateRange[1] + "的搜索结果：\n");
			}
			
			String resultStr = "查找字：" + finding + "\n结果：\n";
			resultStr += "shm: " + result[0][0] + " / " + result[2][0] + " / " + result[1][0] + "\n";
			resultStr += "sjx: " + result[0][1] + " / " + result[2][1] + " / " + result[1][1] + "\n";
			resultStr += "nhx: " + result[0][2] + " / " + result[2][2] + " / " + result[1][2] + "\n";
			resultStr += "xhq: " + result[0][3] + " / " + result[2][3] + " / " + result[1][3] + "\n";
			resultStr += "njh: " + result[0][4] + " / " + result[2][4] + " / " + result[1][4] + "\n";
			resultStr += "ycx: " + result[0][5] + " / " + result[2][5] + " / " + result[1][5] + "\n";
			resultStr += "总计" + result[3][0] + "条消息。\n用时：" + (stopTime - startTime) + "ms。";
			frame_.addText(resultStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void changeFile() {
		if (JFileChooser.APPROVE_OPTION == fileChooser_.showOpenDialog(frame_)) {
			file_ = fileChooser_.getSelectedFile();
			analyzer_.changeFile(file_);
		}
	}
//	private void openFile() {
//		new SuvietLauncher().init();
//	}
	private void setDateRange() {
		dsF_.setVisible(true);
	}
	private void setDateRange(String startDate, String endDate) {
		analyzer_.setDateRange(startDate, endDate);
	}
	private void setMode(int mode) {
		if (mode == LogAnalyzer.DEFAULT_MODE)analyzer_.setToDefaultMode();
	}
	
	class DateSettingFrame extends JFrame {
		private JTextField yearFrom = new JTextField("1970");
		private JTextField monthFrom = new JTextField("1");
		private JTextField dayFrom = new JTextField("1");
		private JTextField yearTo = new JTextField();
		private JTextField monthTo = new JTextField();
		private JTextField dayTo = new JTextField();
		
		DateSettingFrame() {
			super("设置日期范围");
			setIconImage(icon_.getImage());
			
			Calendar dateNow = Calendar.getInstance();
			yearTo.setText(String.valueOf(dateNow.get(Calendar.YEAR)));
			monthTo.setText(String.valueOf(dateNow.get(Calendar.MONTH) + 1));
			dayTo.setText(String.valueOf(dateNow.get(Calendar.DATE)));
			
			Box centerBox = new Box(BoxLayout.Y_AXIS);//主Box
			Box fromBox = new Box(BoxLayout.X_AXIS);//日期起始Box
			Box toBox = new Box(BoxLayout.X_AXIS);//日期结束Box
			Box buttonBox = new Box(BoxLayout.X_AXIS);//按钮Box
			JLabel label1 = new JLabel("从：");
			JLabel label2 = new JLabel("到：");
			JLabel label3 = new JLabel("年");
			JLabel label4 = new JLabel("月");
			JLabel label5 = new JLabel("日");
			JLabel label6 = new JLabel("年");
			JLabel label7 = new JLabel("月");
			JLabel label8 = new JLabel("日");
			JButton yesButton = new JButton("确定");
			JButton cancelButton = new JButton("取消");
			
			yesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String startDate = yearFrom.getText() + '-' + monthFrom.getText() + '-' + dayFrom.getText();
					String endDate = yearTo.getText() + '-' + monthTo.getText() + '-' + dayTo.getText();
					setDateRange(startDate, endDate);
					setVisible(false);
				}
			});
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {setVisible(false);}
			});
			fromBox.add(label1);
			fromBox.add(yearFrom);
			fromBox.add(label3);
			fromBox.add(monthFrom);
			fromBox.add(label4);
			fromBox.add(dayFrom);
			fromBox.add(label5);
			toBox.add(label2);
			toBox.add(yearTo);
			toBox.add(label6);
			toBox.add(monthTo);
			toBox.add(label7);
			toBox.add(dayTo);
			toBox.add(label8);
			buttonBox.add(yesButton);
			buttonBox.add(cancelButton);
			centerBox.add(fromBox);
			centerBox.add(toBox);
			centerBox.add(buttonBox);
			
			getContentPane().add(BorderLayout.CENTER,centerBox);
			setResizable(false);
			setLocation(550,350);
			setSize(200,130);
		}
	}
	
	public static class ShowFrame extends JFrame {
		private JTextArea textArea_ = new JTextArea();
		
		ShowFrame(SuvietLauncher l) {
			super("苏共中央会议资料处理");
			JPanel panel = new JPanel();
			JButton nextButton = new JButton("继续");
			
			JMenuBar menuBar = new JMenuBar();
			JMenu fileMenu = new JMenu("文件");
			JMenuItem changeFileOption = new JMenuItem("更改");
//			JMenuItem openFileOption = new JMenuItem("打开");
			menuBar.add(fileMenu);
			fileMenu.add(changeFileOption);
//			fileMenu.add(openFileOption);
/*			openFileOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {l.openFile();}
			});
*/
			changeFileOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {l.changeFile();}
			});
			
			JMenu configMenu = new JMenu("设置");
			JMenuItem dateSettingOption = new JMenuItem("设置日期范围");
			JMenu modeMenu = new JMenu("设置模式");
			JMenuItem defaultOption = new JMenuItem("设置为默认模式");
			menuBar.add(configMenu);
			configMenu.add(modeMenu);
			configMenu.add(dateSettingOption);
			modeMenu.add(defaultOption);
			dateSettingOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {l.setDateRange();}
			});
			defaultOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {l.setMode(LogAnalyzer.DEFAULT_MODE);}
			});
			
			setIconImage(l.icon_.getImage());
			setJMenuBar(menuBar);
			nextButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {l.nextAnalyze();}
			});
			textArea_.setPreferredSize(new Dimension(350, 500));
			panel.add(textArea_);
			getContentPane().add(BorderLayout.CENTER,panel);
			getContentPane().add(BorderLayout.SOUTH,nextButton);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setLocation(500,300);
			setSize(400,500);
			setVisible(true);
		}
		
		public void addText(String str) {
			textArea_.append(str);
			textArea_.setCaretPosition(textArea_.getText().length());
		}
		public void clearText() {
			textArea_.setText("");
		}
	}
}
