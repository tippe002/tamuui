package application;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame whiteOut = new JFrame();
		whiteOut.setUndecorated(true);
		whiteOut.setExtendedState(Frame.MAXIMIZED_BOTH); 
		whiteOut.setFocusableWindowState(false);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		whiteOut.getContentPane().add(panel);
		whiteOut.setVisible(true);
		State state = State.getState();
		state.getLogin().getFrame().setVisible(true);
		State.getState().log("DISPLAY LOGIN");
	}

}
