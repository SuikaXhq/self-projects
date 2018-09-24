package xhq.mahjong;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MjHelper
{
	private MjMountain mjm;
	private Hand handCurrent;
	private TenPaiCalculator calculator;
	private ArrayList<Integer> handList = new ArrayList<Integer>();//模式1使用的List
	private boolean[] answer = new boolean[9];
	private boolean[] result = new boolean[9];
	private JFrame frame = new JFrame("清一色算听");
	private Box windowBox = Box.createVerticalBox();
	private JPanel handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
	private JPanel answerPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel patternPanel = new JPanel();
	private JLabel[] handLabel = new JLabel[13];
	private JButton[] buttons = new JButton[9];
	private ImageIcon[] pimgs = new ImageIcon[9];
	private ImageIcon[] pimgsC = new ImageIcon[9];//筒
	private ImageIcon[] mimgs = new ImageIcon[9];
	private ImageIcon[] mimgsC = new ImageIcon[9];//万
	private ImageIcon[] simgs = new ImageIcon[9];
	private ImageIcon[] simgsC = new ImageIcon[9];//索
	private ImageIcon[] imgs = new ImageIcon[9];
	private ImageIcon[] imgsC = new ImageIcon[9];//当前外观
	private JPanel buttonPanel = new JPanel();
	private JButton checkButton = new JButton("确定(C)");
	private JButton nextButton = new JButton("下一个(N)");
	private JButton deleteButton = new JButton("删除(D)");
	private JButton deleteAllButton = new JButton("清空(A)");
	private JMenu optionMenu = new JMenu("设置");
	private JMenu modeMenu = new JMenu("模式");
	private JMenu surfaceMenu = new JMenu("外观");
	private HelpFrame helpFrame = new HelpFrame("游戏帮助");
	private JMenuItem[] surface = new JMenuItem[3];
	private JMenuItem[] mode = new JMenuItem[2];
	private int currentSurface = 0;//0=筒子,1=万字,2=索子
	private int currentMode = 0;//0=清一色算听练习,1=清一色算听
	private int handPointer = 0;//输入手牌时的光标位置
	private AnswerListener[] answerListener = new AnswerListener[9];
	public static final JLabel TEN_PAI_LABEL = new JLabel("テンパイ：");
	public static final JLabel NO_TEN_LABEL = new JLabel("ノーテン");
	public static final JLabel CORRECT_LABEL = new JLabel("正确！");
	public static final JLabel WRONG_LABEL = new JLabel("错误！");
	public static JLabel[] pai = new JLabel[9];

	public MjHelper() {
		calculator = new TenPaiCalculator();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(500,300);
		frame.setSize(500,300);
		frame.getContentPane().add(BorderLayout.CENTER,windowBox);
		JMenuBar mb = new JMenuBar();
		JMenu helpMenu = new JMenu("帮助");
		JMenuItem help = new JMenuItem("游戏帮助");
		helpMenu.add(help);
		help.addActionListener(new HelpListener());//游戏帮助菜单
		surface[0] = new JMenuItem("筒");
		surface[1] = new JMenuItem("万");
		surface[2] = new JMenuItem("索");
		for (int i = 0;i < surface.length;i++) {
			surfaceMenu.add(surface[i]);
			surface[i].addActionListener(new SurfaceListener(i));
		}//清一色外观菜单
		mode[0] = new JMenuItem("清一色算听练习");
		mode[1] = new JMenuItem("清一色算听");
		for (int i = 0;i < mode.length;i++) {
			modeMenu.add(mode[i]);
			mode[i].addActionListener(new ModeListener(i));
		}//模式菜单
		optionMenu.add(modeMenu);
		optionMenu.add(surfaceMenu);
		mb.add(optionMenu);
		mb.add(helpMenu);
		frame.setJMenuBar(mb);//菜单栏
		Dimension preferredSize = new Dimension(500,50);
		resultPanel.setPreferredSize(preferredSize);
		handPanel.setPreferredSize(preferredSize);
		for (int i = 0;i < 13;i++) {
			handLabel[i] = new JLabel();
			handPanel.add(handLabel[i]);
		}
		windowBox.add(handPanel);
		windowBox.add(resultPanel);
		windowBox.add(patternPanel);
		windowBox.add(answerPanel);
		windowBox.add(buttonPanel);
		checkButton.addActionListener(new CheckButtonListener());
		checkButton.setMnemonic(KeyEvent.VK_C);//快捷键
		nextButton.addActionListener(new NextButtonListener());
		nextButton.setMnemonic(KeyEvent.VK_N);
		deleteButton.addActionListener(new DeleteButtonListener());
		deleteButton.setMnemonic(KeyEvent.VK_D);
		deleteAllButton.addActionListener(new DeleteAllButtonListener());
		deleteAllButton.setMnemonic(KeyEvent.VK_A);
		buttonPanel.add(checkButton);
		buttonPanel.add(nextButton);
		nextButton.setEnabled(false);
		try//导入图片
		{
			for (int i = 0;i < 9;i++)
			{
				URL path = this.getClass().getResource("mj/");
				pimgs[i] = new ImageIcon(new URL(path,(i + 1) + "p.gif"));
				pimgsC[i] = new ImageIcon(new URL(path,(i + 1) + "pC.gif"));
				mimgs[i] = new ImageIcon(new URL(path,(i + 1) + "m.gif"));
				mimgsC[i] = new ImageIcon(new URL(path,(i + 1) + "mC.gif"));
				simgs[i] = new ImageIcon(new URL(path,(i + 1) + "s.gif"));
				simgsC[i] = new ImageIcon(new URL(path,(i + 1) + "sC.gif"));
				pai[i] = new JLabel(pimgs[i]);
				buttons[i] = new JButton(pimgs[i]);//默认筒子外观
				buttons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				buttons[i].setBorder(null);
				buttons[i].setContentAreaFilled(false);
				answerListener[i] = new AnswerListener(i);
				buttons[i].addActionListener(answerListener[i]);
				buttons[i].setMnemonic(49 + i);//快捷键
				answerPanel.add(buttons[i]);
			}
			imgs = pimgs;
			imgsC = pimgsC;
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		frame.setIconImage(pimgs[0].getImage());
		frame.setVisible(true);
	}

	public void start() {
		mjm = new MjMountain();
		handCurrent = mjm.getHand();
		paintHand();
		for (int i = 0;i < 9;i++)
		{
			if (answer[i])
				answerListener[i].refresh();//还原按钮
		}
		frame.repaint();
	}

	private void paintHand() {
		int i = 1;
		int ih = 0;
		for (JLabel jl:handLabel)
			jl.setIcon(null);//清除
		while (i <= 9)
		{
			while (i <= 9 && handCurrent.getHand().get(i) == 0)
				i++;//scan
			if (i <= 9)
			{
				for (int j = 1;j <= handCurrent.getHand().get(i);j++) {
					handLabel[ih].setIcon(imgs[i-1]);//构造手牌
					ih++;
				}
				i++;
			}
		}
	}

	public void next() {
		nextButton.setEnabled(false);
		checkButton.setEnabled(true);
		for (int i = 0;i < 9;i++) {
			buttons[i].setEnabled(true);
			pai[i].setIcon(imgs[i]);
		}
		resultPanel.removeAll();
		start();
		frame.repaint();
		frame.validate();
	}

	public void check() {
		result = calculator.calc(handCurrent);
		if (currentMode == 0)
		{
			for (JButton jb:buttons)
				jb.setEnabled(false);
			nextButton.setEnabled(true);
			checkButton.setEnabled(false);
			if (Arrays.equals(answer,result))
				resultPanel.add(CORRECT_LABEL);
			else
				resultPanel.add(WRONG_LABEL);
		}
		if (currentMode == 1)
		{
			Collections.sort(handList);
			paintHand();
		}
		boolean isTenPai = false;
		for (boolean b:result)
		{
			if (b)
				isTenPai = true;
		}
		if (isTenPai)
		{
			resultPanel.add(TEN_PAI_LABEL);
			for (int i = 0;i < 9;i++)
			{
				if (result[i])
					resultPanel.add(pai[i]);
			}
		} else {
			resultPanel.add(NO_TEN_LABEL);
		}
		frame.repaint();
		frame.validate();
	}

	private void changeSurface() {
		switch (currentSurface)
		{
		case 0:
			imgs = pimgs;
			imgsC = pimgsC;
			break;
		case 1:
			imgs = mimgs;
			imgsC = mimgsC;
			break;
		case 2:
			imgs = simgs;
			imgsC = simgsC;
			break;
		}
		for (AnswerListener al:answerListener)
			al.refresh();
		for (int i = 0;i < 9;i++)
			pai[i].setIcon(imgs[i]);
		paintHand();
	}

	public class AnswerListener implements ActionListener
	{
		private final int index;
		private boolean status;

		public AnswerListener(int i) {
			index = i;
			status = false;
		}

		public void refresh() {
				status = false;
				answer[index] = false;
				buttons[index].setIcon(imgs[index]);
		}

		public void actionPerformed(ActionEvent e) {
			switch (currentMode)
			{
			case 0:
				if (status)
				{
					buttons[index].setIcon(imgs[index]);
					answer[index] = false;
					status = false;
				} else {
					buttons[index].setIcon(imgsC[index]);
					answer[index] = true;
					status = true;
				}
				break;
			case 1:
				if (handPointer <= 12 && handCurrent.add(index + 1))
				{
					handLabel[handPointer].setIcon(imgs[index]);
					handList.add(index + 1);
					handPointer++;
				}
				resultPanel.removeAll();
				frame.repaint();
				break;
			}
		}
	}

	public class DeleteButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if (handList.size() > 0 && handCurrent.del(handList.get(handList.size() - 1)))
			{
				handList.remove(handList.size() - 1);
				handPointer--;
				handLabel[handPointer].setIcon(null);
			}
			resultPanel.removeAll();
			frame.repaint();
		}
	}

	public class DeleteAllButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			handList.clear();
			resultPanel.removeAll();
			handPointer = 0;
			handCurrent = new Hand();
			paintHand();
			frame.repaint();
		}
	}

	public class NextButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			next();
		}
	}

	public class CheckButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			check();
		}
	}

	public class HelpListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			helpFrame.setVisible(true);
		}
	}

	public class SurfaceListener implements ActionListener
	{
		int index;
		
		public SurfaceListener(int i) {
			index = i;
		}

		public void actionPerformed(ActionEvent e) {
			if (currentSurface != index)
			{
				currentSurface = index;
				changeSurface();
			}
		}
	}

	public class ModeListener implements ActionListener
	{
		private final int index;

		public ModeListener(int i) {
			index = i;
		}

		public void actionPerformed(ActionEvent e) {
			if (currentMode != index)
			{
				resultPanel.removeAll();
				for (JLabel jl:handLabel)
					jl.setIcon(null);
				currentMode = index;
				handCurrent = new Hand();
				switch (index)
				{
				case 0:
					buttonPanel.remove(deleteButton);
					buttonPanel.remove(deleteAllButton);
					buttonPanel.add(nextButton);
					nextButton.setEnabled(false);
					next();
					break;
				case 1:
					for (int i = 0;i < 9;i++)
					{
						if (answer[i])
							answerListener[i].refresh();//还原按钮
					}
					buttonPanel.remove(nextButton);
					buttonPanel.add(deleteButton);
					buttonPanel.add(deleteAllButton);
					checkButton.setEnabled(true);
					handPointer = 0;
					break;
				}
				frame.repaint();
				frame.validate();
			}
		}
	}

	public class HelpFrame extends JFrame
	{
		public HelpFrame(String title) {
			super(title);
			setBounds(600,400,400,150);
			setResizable(false);
			JLabel jl = new JLabel("<html><body>上部：手牌<br>中部：选择你认为的听牌（未听则不选择）或输入手牌<br>快捷键为alt+数字/字母<body></html>");
			getContentPane().add(jl);
		}
	}
}