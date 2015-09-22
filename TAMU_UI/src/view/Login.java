package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;

import application.State;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


public class Login {

	private JFrame frame;
	private JTextField participantNameTextField;
	private JTextField uinTextField;
	private JTextField departmentTextField;
	private JTextField vechicleIdTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JFrame getFrame(){ return this.frame; }

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.getContentPane().setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {200, 0, 0};
		gridBagLayout.rowHeights = new int[]{78, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout); 
		
		
		JLabel LoginHeader = new JLabel("<html><h1 style=\"text-align:center\">Texas A&M<br>Human Factor Laboratory</h1></html>",SwingConstants.CENTER);
		GridBagConstraints gbc_LoginHeader = new GridBagConstraints();
		gbc_LoginHeader.insets = new Insets(0, 0, 5, 0);
		gbc_LoginHeader.gridwidth = 3;
		gbc_LoginHeader.anchor = GridBagConstraints.NORTH;
		gbc_LoginHeader.fill = GridBagConstraints.HORIZONTAL;
		gbc_LoginHeader.gridx = 0;
		gbc_LoginHeader.gridy = 0;
		frame.getContentPane().add(LoginHeader, gbc_LoginHeader);
		
		JLabel participantNameLbl = new JLabel("<html><h3>Participant Name:</h3></html>");
		participantNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_participantNameLbl = new GridBagConstraints();
		gbc_participantNameLbl.anchor = GridBagConstraints.NORTHEAST;
		gbc_participantNameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_participantNameLbl.gridx = 0;
		gbc_participantNameLbl.gridy = 1;
		frame.getContentPane().add(participantNameLbl, gbc_participantNameLbl);
		
		participantNameTextField = new JTextField();
		participantNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_participantNameTextField = new GridBagConstraints();
		gbc_participantNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_participantNameTextField.anchor = GridBagConstraints.WEST;
		gbc_participantNameTextField.gridx = 1;
		gbc_participantNameTextField.gridy = 1;
		frame.getContentPane().add(participantNameTextField, gbc_participantNameTextField);
		participantNameTextField.setColumns(10);
		
		JLabel uinLbl = new JLabel("<html><h3>UIN:</h3></html>");
		uinLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_uinLbl = new GridBagConstraints();
		gbc_uinLbl.anchor = GridBagConstraints.NORTHEAST;
		gbc_uinLbl.insets = new Insets(0, 0, 5, 5);
		gbc_uinLbl.gridx = 0;
		gbc_uinLbl.gridy = 2;
		frame.getContentPane().add(uinLbl, gbc_uinLbl);
		
		uinTextField = new JTextField();
		uinTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_uinTextField = new GridBagConstraints();
		gbc_uinTextField.anchor = GridBagConstraints.WEST;
		gbc_uinTextField.insets = new Insets(0, 0, 5, 5);
		gbc_uinTextField.gridx = 1;
		gbc_uinTextField.gridy = 2;
		frame.getContentPane().add(uinTextField, gbc_uinTextField);
		uinTextField.setColumns(10);
		
		JLabel DepartmentLbl = new JLabel("<html><h3>Department:</h3></html>");
		DepartmentLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_DepartmentLbl = new GridBagConstraints();
		gbc_DepartmentLbl.anchor = GridBagConstraints.EAST;
		gbc_DepartmentLbl.insets = new Insets(0, 0, 5, 5);
		gbc_DepartmentLbl.gridx = 0;
		gbc_DepartmentLbl.gridy = 3;
		frame.getContentPane().add(DepartmentLbl, gbc_DepartmentLbl);
		
		departmentTextField = new JTextField();
		departmentTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_departmentTextField = new GridBagConstraints();
		gbc_departmentTextField.anchor = GridBagConstraints.WEST;
		gbc_departmentTextField.insets = new Insets(0, 0, 5, 5);
		gbc_departmentTextField.gridx = 1;
		gbc_departmentTextField.gridy = 3;
		frame.getContentPane().add(departmentTextField, gbc_departmentTextField);
		departmentTextField.setColumns(10);
		
		JLabel VehicleIDLbl = new JLabel("<html><h3>Vehicle ID:</h3></html>");
		VehicleIDLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_VehicleIDLbl = new GridBagConstraints();
		gbc_VehicleIDLbl.anchor = GridBagConstraints.EAST;
		gbc_VehicleIDLbl.insets = new Insets(0, 0, 5, 5);
		gbc_VehicleIDLbl.gridx = 0;
		gbc_VehicleIDLbl.gridy = 4;
		frame.getContentPane().add(VehicleIDLbl, gbc_VehicleIDLbl);
		
		vechicleIdTextField = new JTextField();
		vechicleIdTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		vechicleIdTextField.setEditable(false);
		vechicleIdTextField.setText("TAMU001");
		GridBagConstraints gbc_vechicleIdTextField = new GridBagConstraints();
		gbc_vechicleIdTextField.anchor = GridBagConstraints.WEST;
		gbc_vechicleIdTextField.insets = new Insets(0, 0, 5, 5);
		gbc_vechicleIdTextField.gridx = 1;
		gbc_vechicleIdTextField.gridy = 4;
		frame.getContentPane().add(vechicleIdTextField, gbc_vechicleIdTextField);
		vechicleIdTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		final Login that = this;
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				State state = State.getState();
				state.getStatus().getFrame().setVisible(true);
				that.getFrame().setVisible(false);
				State.getState().log("USER LOGGED IN");
				State.getState().log("DISPLAY STATUS");
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.ipady = 5;
		gbc_btnNewButton.ipadx = 10;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		gbc_btnNewButton.gridwidth = 3;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		frame.setBounds(100, 100, 400, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
