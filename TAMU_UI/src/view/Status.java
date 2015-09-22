package view;


import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import model.StatusModel;

import application.State;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

public class Status {

	private JFrame frame;
	private JTable table;
	private JButton statusButton;
	private JButton searchButton;
	private JButton notificationButton;
	private StatusModel model;
	private JButton navigationButton;

	public Status( StatusModel model ) {
		this.model = model;
		initialize();
	}
	
	public void setNofiticationButtonColor( Color color ){ notificationButton.setBackground(color); }
	
	public JFrame getFrame(){ return this.frame; }

	private Object [][] formatTableData( Object [][] data ){
		Object [][] out = data.clone();
		int lastCol = data[0].length-1;
		for( int i=0;i<data.length;i++ ){
			if( ((String)data[i][lastCol]).toLowerCase().equals("no") )
				out[i][lastCol] = "<html><b color='green'>"+out[i][lastCol]+"</b></html>";
			else
				out[i][lastCol] = "<html><b color='red'>"+out[i][lastCol]+"</b></html>";
		}
		return out;
	}
	
	public void refreshTable(){
		
	}
	
	public void toggleBackup( int record_id ){
		String current = (String)table.getValueAt(record_id, 4);
		if( current.toUpperCase().contains("NO"))
			table.setValueAt("<html><b color='red'>Yes</b></html>",record_id, 4);
		else
			table.setValueAt("<html><b color='green'>No</b></html>",record_id, 4);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("Status");
		frame.setBounds(100, 100, 800, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//KeyboardFocusManager mgr = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		//mgr.addKeyEventDispatcher( new State.HotKeys(this.getFrame()) );
		
		statusButton = new JButton("Status");
		statusButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		searchButton = new JButton("License Plate Search");
		final Status that = this;
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				State.getState().getSearch().getFrame().setLocation( that.frame.getLocation() );
				State.getState().getSearch().getFrame().setVisible(true);
				that.getFrame().setVisible(false);
				State.getState().log("DISPLAY SEARCH");
			}
		});
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
		notificationButton.setBackground( Color.GRAY );
		notificationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.WHITE);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JTableHeader th = table.getTableHeader();
		th.setReorderingAllowed(false);
		table.setTableHeader(th);
		table.setModel(new DefaultTableModel(
			this.formatTableData(model.getDisplayValues() )
			,
			new String[] {
				"Serial No.", "Vehicle Number", "Vehicle Status", "Location", "Backup"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		scrollPane.setViewportView(table);
		
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
					.addGap(60)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(statusButton)
							.addGap(51)
							.addComponent(searchButton)
							.addGap(78)
							.addComponent(notificationButton)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(navigationButton, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)))
					.addGap(60))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(statusButton)
						.addComponent(searchButton)
						.addComponent(notificationButton)
						.addComponent(navigationButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
					.addGap(61))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
	}
}
