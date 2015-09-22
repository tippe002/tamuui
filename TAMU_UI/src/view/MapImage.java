package view;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.MapImageModel;
import model.NotificationsModel;

import application.State;
import javax.swing.UIManager;

public class MapImage {

	private JFrame frame;
	JButton notificationButton;
	private MapImageModel model;
	private ImageIcon image;
	private JLabel lblMapIcon;

	public MapImage(MapImageModel model) {
		this.model = model;
		initialize();
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String [] maps = {"src/images/1.png"};
					MapImage window = new MapImage( new MapImageModel(maps) );
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void refresh(){
		try{ 
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(model.getMapImage()));
			lblMapIcon.setIcon( new ImageIcon(image) );
		} catch ( Exception e ){
			System.out.println( "Could not load image: " + model.getMapImage() );
			e.printStackTrace();
		}
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
		
		final MapImage that = this;
		
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
		notificationButton.setBackground(UIManager.getColor("Button.background"));
		notificationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				State.getState().getNotifications().getFrame().setLocation( that.frame.getLocation() );
				State.getState().getNotifications().getFrame().setVisible(true);
				that.getFrame().setVisible(false);
				State.getState().log("DISPLAY NOTIFICATIONS");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		JButton btnNavigation = new JButton("Navigation");
		btnNavigation.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNavigation.setBackground(Color.GRAY);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(60)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(statusButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addGap(39)
							.addComponent(searchButton)
							.addGap(18)
							.addComponent(notificationButton, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNavigation, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(60, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(statusButton)
						.addComponent(searchButton)
						.addComponent(btnNavigation, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(notificationButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(40, Short.MAX_VALUE))
		);
		
		//image = new ImageIcon(model.getMapImage());
		try{ 
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(model.getMapImage()));
			lblMapIcon = new JLabel( new ImageIcon(image) );
		} catch ( Exception e ){
			System.out.println( "Could not load image: " + model.getMapImage() );
			e.printStackTrace();
		}
		//lblMapIcon = new JLabel( new ImageIcon(model.getMapImage()) );
		scrollPane.setViewportView(lblMapIcon);
		frame.getContentPane().setLayout(groupLayout);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
	}
}
