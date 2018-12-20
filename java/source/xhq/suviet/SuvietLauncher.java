/*
 * Class SuvietLauncher
 * Ver0.0.1
 * Feature: 输入查找的词，返回次数、消息总数及每个人的详细情况
 * 
 * //TODO: 切换文件
 */

package xhq.suviet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

public class SuvietLauncher {
	private LogAnalyzer analyzer_ = new LogAnalyzer();
	private ImageIcon icon_ = new ImageIcon(this.getClass().getResource("res/1.jpg"));
	private ShowFrame frame_ = new ShowFrame(this);
	private JFileChooser fileChooser_ = new JFileChooser(frame_.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	private File file_ = new File("");
	
	
	public static void main(String[] args) {
		new SuvietLauncher().init();
	}
	
	public void init() {
		fileChooser_.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fileChooser_.showOpenDialog(frame_))
			file_ = fileChooser_.getSelectedFile();
		nextAnalyze();
	}
	
	public void nextAnalyze() {
		frame_.clearText();
		try {
			String finding = JOptionPane.showInputDialog("请键入查找的词。");
			if (finding == null || finding.equals(""))return;
			analyzer_.reset(file_, finding);
			int[][] result = analyzer_.analyze();
			String resultStr = "查找字：" + finding + "\n结果：\n";
			resultStr += "shm: " + result[0][0] + " / " + result[1][0] + "\n";
			resultStr += "sjx: " + result[0][1] + " / " + result[1][1] + "\n";
			resultStr += "nhx: " + result[0][2] + " / " + result[1][2] + "\n";
			resultStr += "xhq: " + result[0][3] + " / " + result[1][3] + "\n";
			resultStr += "njh: " + result[0][4] + " / " + result[1][4] + "\n";
			resultStr += "ycx: " + result[0][5] + " / " + result[1][5] + "\n";
			resultStr += "总计" + result[2][0] + "条消息。";
			frame_.addText(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class ShowFrame extends JFrame {
		private JTextArea textArea_ = new JTextArea();
		
		ShowFrame(SuvietLauncher l) {
			JFrame frame = new JFrame("苏共中央会议资料处理");
			JPanel panel = new JPanel();
			JButton button = new JButton("继续");
			frame.setIconImage(l.icon_.getImage());
			button.addActionListener(l.new NextListener());
			textArea_.setPreferredSize(new Dimension(350, 500));
			panel.add(textArea_);
			frame.getContentPane().add(BorderLayout.CENTER,panel);
			frame.getContentPane().add(BorderLayout.SOUTH,button);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocation(500,300);
			frame.setSize(400,700);
			frame.setVisible(true);
		}
		
		public void addText(String str) {
			textArea_.append(str);
			textArea_.setCaretPosition(textArea_.getText().length());
		}
		
		public void clearText() {
			textArea_.setText("");
		}
	}
	
	class NextListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			nextAnalyze();
		}
	}

}
