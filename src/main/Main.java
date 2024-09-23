package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();	// Creates a window for the game (we will not be operating off the terminal).
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// So that the window closes properly by clicking 'X'.
		window.setResizable(false);	// So that the window cannot be resized.
		window.setTitle("2D Game");	// Names the window
		
		// Make a game panel using our class 'GamePanel'.
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);	// This adds the panel to the frame. 
		
		window.pack();	// This causes the frame to fit to the dimensions of the panel (which is the subcomponent).
		
		window.setLocationRelativeTo(null);	// The window will display at the center of the screen.
		window.setVisible(true);	// Makes the window visible. 
		
		gamePanel.setupGame();
		gamePanel.startGameThread();	// Starts the game clock, so to speak.
		
	}

}
