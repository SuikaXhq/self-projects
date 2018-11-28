/*
 * 解决杰哥的D10四则运算结果尾数概率问题
 * Ver0.0.1
 */
package xhq.D10;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;

public class D10Frame extends JFrame {

	private JPanel contentPane;
	JList list1;
	JList list2;
	JList list3;
	JList list4;
	JList[] lists = new JList[4];
	JTextArea textArea;
	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D10Frame frame = new D10Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public D10Frame() {
		setResizable(false);
		setTitle("D10 Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel label = new JLabel("{ [ ( D10 ");
		panel_1.add(label);
		
		list1 = new JList();
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.setModel(new AbstractListModel() {
			String[] values = new String[] {"+", "-", "*", "/", "None"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list1.setVisibleRowCount(5);
		list1.setValueIsAdjusting(true);
		panel_1.add(list1);
		
		JLabel lblD = new JLabel(" D10 ) ");
		panel_1.add(lblD);
		
		list2 = new JList();
		list2.setModel(new AbstractListModel() {
			String[] values = new String[] {"+", "-", "*", "/", "None"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list2.setVisibleRowCount(5);
		list2.setValueIsAdjusting(true);
		panel_1.add(list2);
		
		JLabel lblD_1 = new JLabel(" D10 ] ");
		panel_1.add(lblD_1);
		
		list3 = new JList();
		list3.setModel(new AbstractListModel() {
			String[] values = new String[] {"+", "-", "*", "/", "None"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list3.setVisibleRowCount(5);
		list3.setValueIsAdjusting(true);
		panel_1.add(list3);
		
		JLabel lblD_2 = new JLabel(" D10 } ");
		panel_1.add(lblD_2);
		
		list4 = new JList();
		list4.setModel(new AbstractListModel() {
			String[] values = new String[] {"+", "-", "*", "/", "None"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_1.add(list4);
		
		JLabel lblD_3 = new JLabel(" D10");
		panel_1.add(lblD_3);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				D10Calculator.go();
			}
		});
		panel.add(btnCalculate);
		
		JLabel lblResult = new JLabel("Result:");
		contentPane.add(lblResult, BorderLayout.WEST);
		
		lists[0] = list1;
		lists[1] = list2;
		lists[2] = list3;
		lists[3] = list4;
		
		textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		textArea.setPreferredSize(new Dimension(300,200));
	}

}
