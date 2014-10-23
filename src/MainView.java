import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;


public class MainView extends JFrame {

	private JPanel contentPane;
	private JTextField set1;
	private JTextField set2;
	private static SetLogic logic;
	private static int inputCounter;
	private JTextField setName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		inputCounter = 0;
		logic = new SetLogic();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/pics/icon.png")));
		setTitle("SetOperator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JTextArea resultArea = new JTextArea("Operations Field:\n-----------------------");
		final JTextArea inputArea = new JTextArea();
		inputArea.setText("Insert the universe set.");
		final JTextArea dataArea = new JTextArea("Data Field:\n---------------");
		setName = new JTextField();
		setName.setHorizontalAlignment(SwingConstants.CENTER);
		setName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		setName.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		JSeparator separator = new JSeparator();
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
					if(logic.validate(inputArea.getText(),setName.getText()))
					{
						logic.addSet(inputArea.getText(),setName.getText());
						
						if(logic.lastSetIndex==1) dataArea.setText(dataArea.getText()+"\n"+setName.getText()+" = "+logic.setToString()+"\t(Universe Set)");
						else dataArea.setText(dataArea.getText()+"\n"+setName.getText()+" = "+logic.setToString());
						inputArea.setText("Insert Subset.");
						setName.setText("");
					}
			}
		});
		enter.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		set1 = new JTextField();
		set1.setHorizontalAlignment(SwingConstants.CENTER);
		set1.setColumns(1);
		
		set2 = new JTextField();
		set2.setHorizontalAlignment(SwingConstants.CENTER);
		set2.setColumns(1);
		
		JButton intersect = new JButton("Intersect");
		intersect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(logic.validateOperation(set1.getText(),set2.getText(),1))
				{
					HashSet intersection = logic.operations(set1.getText(),set2.getText(),1);
					resultArea.setText(resultArea.getText()+"\n"+set1.getText()+" Intersect "+set2.getText()+" = "+logic.setToString(intersection));
				}
			}
		});
		
		
		
		JButton union = new JButton("Union");
		union.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(logic.validateOperation(set1.getText(),set2.getText(),2))
				{
					HashSet union = logic.operations(set1.getText(),set2.getText(),2);
					resultArea.setText(resultArea.getText()+"\n"+set1.getText()+" Union "+set2.getText()+" = "+logic.setToString(union));
				}
				
			}
		});
		
		JButton firstComplement = new JButton("1st Complement");
		firstComplement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(logic.validateOperation(set1.getText(),set2.getText(),3))
				{
					HashSet firstComplement = logic.operations(set1.getText(),set2.getText(),3);
					resultArea.setText(resultArea.getText()+"\n"+set1.getText()+" Complement = "+logic.setToString(firstComplement));
				}
			}
		});
		
		JButton secondComplement = new JButton("2nd Complement");
		secondComplement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(logic.validateOperation(set1.getText(),set2.getText(),4))
				{
					HashSet secondComplement = logic.operations(set1.getText(),set2.getText(),4);
					resultArea.setText(resultArea.getText()+"\n"+set2.getText()+" Complement = "+logic.setToString(secondComplement));
				}
			}
		});
		

		
		JLabel lblstSet = new JLabel("1st Set:");
		lblstSet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblndSet = new JLabel("2nd Set:");
		lblndSet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				dataArea.setText("Data Field:\n---------------");
				resultArea.setText("Operations Field:\n-----------------------");
				inputArea.setText("Insert the universe set.");
				set1.setText("");
				set2.setText("");
				inputCounter = 0;
				logic = new SetLogic();
			}
		});
		
		JLabel lblInsertionFormatabf = new JLabel("Insertion Format: {a,1,2,bf,123}        Empty set: Phi");
		lblInsertionFormatabf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblSetName = new JLabel("Set Name");
		lblSetName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblstSet)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(set1, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblndSet)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(set2, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(firstComplement)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(secondComplement)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(union)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(intersect))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(setName, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(56)
									.addComponent(lblSetName)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInsertionFormatabf)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 494, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(reset, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
								.addComponent(enter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(enter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(setName, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInsertionFormatabf)
						.addComponent(lblSetName)
						.addComponent(reset))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(intersect)
							.addComponent(union)
							.addComponent(secondComplement)
							.addComponent(firstComplement)
							.addComponent(lblstSet)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(set2, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
								.addComponent(lblndSet)
								.addComponent(set1, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)))
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
					.addGap(27))
		);
		
		
		resultArea.setEditable(false);
		resultArea.setLineWrap(true);
		scrollPane_2.setViewportView(resultArea);
		
		
		inputArea.setFont(new Font("Courier New", Font.PLAIN, 18));
		scrollPane_1.setViewportView(inputArea);
		
		
		dataArea.setEditable(false);
		dataArea.setLineWrap(true);
		scrollPane.setViewportView(dataArea);
		contentPane.setLayout(gl_contentPane);
	}
}
