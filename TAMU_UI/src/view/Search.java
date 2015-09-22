package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.SearchModel;

import application.State;
import javax.swing.UIManager;

public class Search {

	private JFrame frame;
	private JTextField textField;
	private JTable table;
	private JButton notificationButton;
	private JButton pullOverButton;
	private JButton continueButton;
	private SearchModel model;
	private JButton navigationButton;

	public Search( SearchModel model ) {
		this.model = model;
		initialize();
	}
	
	public void setNofiticationButtonColor( Color color ){ notificationButton.setBackground(color); }
	public void disablePullOverContinue( ){ 
		pullOverButton.setEnabled(false);
		continueButton.setEnabled(false);
	}
	
	public JFrame getFrame(){ return this.frame; }
	private void renderResult( Object [] result ){
		for( int i = 0; i < table.getRowCount(); i++ ){
			table.setValueAt(result == null ? null : result[i], i,1);
			table.setValueAt(result == null ? null : result[i+table.getRowCount()], i,3);
		}
		if( result != null ){
			pullOverButton.setEnabled(true);
			continueButton.setEnabled(true);
		} else {
			pullOverButton.setEnabled(false);
			continueButton.setEnabled(false);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setTitle("License Plate Search");
		frame.setBounds(100, 100, 800, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//KeyboardFocusManager mgr = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		//mgr.addKeyEventDispatcher( new State.HotKeys(this.getFrame()) );
		
		JButton statusButton = new JButton("Status");
		final Search that = this;
		statusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				State.getState().getStatus().getFrame().setLocation( that.frame.getLocation() );
				State.getState().getStatus().getFrame().setVisible(true);
				that.getFrame().setVisible(false);
				State.getState().log("DISPLAY STATUS");
			}
		});
		statusButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton searchButton = new JButton("License Plate Search");
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		notificationButton = new JButton("Notifications");
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				State.getState().getNotifications().getFrame().setLocation( that.frame.getLocation() );
				State.getState().getNotifications().getFrame().setVisible(true);
				that.getFrame().setVisible(false);
				State.getState().log("DISPLAY NOTIFICATIONS");
			}
		});
		notificationButton.setBackground(Color.GRAY);
		notificationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel vechicleLicensePlateNumberLbl = new JLabel("Vehicle License Plate Number:");
		vechicleLicensePlateNumberLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if( Character.isLowerCase(e.getKeyChar())) e.setKeyChar( Character.toUpperCase( e.getKeyChar() ) );
			}
		});
		textField.setColumns(10);
		
		pullOverButton = new JButton("Pull Over");
		pullOverButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		//pullOverButton.setEnabled(false);
		pullOverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				State.getState().getSearch().disablePullOverContinue();
				State.getState().log("PULL OVER");
			}
		});
		
		continueButton = new JButton("Continue");
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		//continueButton.setEnabled(false);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				State.getState().getSearch().disablePullOverContinue();
				State.getState().log("CONTINUE");
			}
		});
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"<html><b>Vehicle No.</b></html>", null, "<html><b>Owner Name:</b></html>", null},
				{"<html><b>Registration State:</b></html>", null, "<html><b>Address:</b></html>", null},
				{"<html><b>Make:</b></html>", null, "<html><b>Owned Since:</b></html>", null},
				{"<html><b>Model:</b></html>", null, "<html><b>Vehicle Status:</b></html>", null},
				{"<html><b>Type:</b></html>", null, "<html><b>Traffic Violations:</b></html>", null},
				{"<html><b>Color:</b></html>", null, "<html><b>Open Violations:</b></html>", null},
				{"<html><b>Insurance No.</b></html>", null, "<html><b>Date of Last Infraction:</b></html>", null},
				{"<html><b>Special Notes:</b></html>", null, "<html><b>Last Infraction Details:</b></html>", null},
			},
			new String[] {
				"Lbl1", "Data1", "Lbl2", "Data2"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(111);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(129);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(136);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(169);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		renderResult(null);
		table.getColumn("Lbl1").setCellRenderer( new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
				JLabel jl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				if( row == 1 )
					jl.setBackground( Color.LIGHT_GRAY );
				return jl;
			};
		});
		table.getColumn("Lbl2").setCellRenderer( new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
				JLabel jl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				if( row == 3 )
					jl.setBackground( Color.LIGHT_GRAY );
				return jl;
			};
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				State.getState().log("SEARCH TAG " + textField.getText().toUpperCase());
				Object [] result = model.findRow("vehicle_no",textField.getText().toUpperCase());
				if( result == null ){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(frame, "Vehicle number not found!" );
				}
				renderResult( result );
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		navigationButton = new JButton("Navigation");
		navigationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				State.getState().getMapsModel().nextMapImage();
				State.getState().getMaps().refresh();
				State.getState().log("UPDATE NAVIGATION MAP");
				
				State.getState().getMaps().getFrame().setLocation( that.frame.getLocation() );
				State.getState().getMaps().getFrame().setVisible(true);
				that.getFrame().setVisible(false);
				State.getState().log("DISPLAY NAVIGATION");
			}
		});
		navigationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		navigationButton.setBackground(UIManager.getColor("Button.background"));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(64)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(table, GroupLayout.PREFERRED_SIZE, 685, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(pullOverButton)
									.addPreferredGap(ComponentPlacement.RELATED, 353, Short.MAX_VALUE)
									.addComponent(continueButton)
									.addGap(8))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnSearch)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(statusButton)
												.addGap(18)
												.addComponent(searchButton))
											.addComponent(vechicleLicensePlateNumberLbl)))
									.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
											.addGap(51))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(notificationButton, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
											.addGap(57)))))
							.addGap(18)
							.addComponent(navigationButton, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addGap(50)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(statusButton)
						.addComponent(searchButton)
						.addComponent(navigationButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(notificationButton))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(vechicleLicensePlateNumberLbl))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSearch)
					.addGap(18)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pullOverButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(continueButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
	}
}
