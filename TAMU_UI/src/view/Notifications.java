package view;


import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.NotificationsModel;

import application.State;
import javax.swing.UIManager;

public class Notifications {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private boolean scrollable = false;
	JButton notificationButton;
	private NotificationsModel model;

	public Notifications(NotificationsModel model) {
		this.model = model;
		initialize();
	}
	
	//public void setNotificationRed(){ notificationButton.setBackground(Color.RED); }
	//public void setNotificationYellow(){ notificationButton.setBackground(Color.YELLOW); }
	public void setNofiticationButtonColor( Color color ){ notificationButton.setBackground(color); }
	
	public Object [][] formatTableData( Object [][] data ){
		int lastCol = data[0].length-1;
		Object [][] out = new Object[data.length][data[0].length];
		for( int j=0;j<data.length;j++)
			for( int i=0;i<data[j].length;i++){
				out[j][i]= new String( (String)data[j][i] );
				if( i == lastCol ){
					if( ((String)out[j][i]).toUpperCase().equals("ACCEPTED") )
						out[j][lastCol] = "Accepted";
					else
						out[j][lastCol] = "Accept";
				}
			}
		return out;
	}
	
	public void refreshTable(){
		table.setModel(new DefaultTableModel(
				formatTableData(model.getDisplayValues()),
				new String[] {
					"SL.", "Instructions", "Time", "Acknowledge"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
					false, false,false,false
				};
				
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumn("Acknowledge").setCellRenderer( new DefaultTableCellRenderer(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
					JLabel jl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
					if( ((String)value).equals("Accepted") )
						jl.setBackground( Color.GREEN );
					if( ((String)value).equals("Accept") && ((String)model.getCell(row, col)).equals("LOW") )
						jl.setBackground( Color.YELLOW );
					if( ((String)value).equals("Accept") && ((String)model.getCell(row, col)).equals("HIGH") )
						jl.setBackground( Color.RED );
					return jl;
				};
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(294);
			table.addMouseListener(new MouseAdapter() {
				@Override
				  public void  	mousePressed(MouseEvent e) {
				    if (e.getClickCount() == 2) {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				      if( column == target.getColumnCount()-1 ){
				    	  State.getState().getNotificationsModel().accept(row);
				    	  refreshTable();
						  State.getState().getStatus().setNofiticationButtonColor( State.getState().getNotificationsModel().getNotificationColor() );
						  State.getState().getSearch().setNofiticationButtonColor( State.getState().getNotificationsModel().getNotificationColor() );
						  State.getState().getNotifications().setNofiticationButtonColor( State.getState().getNotificationsModel().getNotificationColor() );
				      }
				    }
				  }
				});
	}
	
	public JFrame getFrame(){ return this.frame; }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("Notifications");
		frame.setBounds(100, 100, 800, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final Notifications that = this;
		
		JButton statusButton = new JButton("Status");
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
		notificationButton.setBackground(Color.GRAY);
		notificationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.WHITE);
		 scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		     public void adjustmentValueChanged(AdjustmentEvent e) {  
		         if( State.getState().getNotifications().isScrollable() == false ){
		        	 e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		        	 State.getState().getNotifications().setScrollable(true); 
		         }
		     }
		 });
		scrollPane.getViewport().setBackground(Color.WHITE);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JTableHeader th = table.getTableHeader();
		th.setReorderingAllowed(false);
		table.setTableHeader(th);
		refreshTable();
		scrollPane.setViewportView(table);
		
		JLabel lblHighPriority = new JLabel("High Priority");
		lblHighPriority.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		
		JLabel lblLowPriority = new JLabel("Low Priority");
		lblLowPriority.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.YELLOW);
		
		JButton navigationButton = new JButton("Navigation");
		navigationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		navigationButton.setBackground(UIManager.getColor("Button.background"));
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
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(60)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(statusButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addGap(39)
							.addComponent(searchButton)
							.addGap(18)
							.addComponent(notificationButton, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(navigationButton, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblHighPriority)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addGap(122)
							.addComponent(lblLowPriority)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(60, Short.MAX_VALUE))
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblHighPriority, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addGap(140))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLowPriority, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
							.addContainerGap(142, Short.MAX_VALUE))))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
	}
	public boolean isScrollable() {
		return scrollable;
	}
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
}
