package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	
	boolean checkDrawTime = false;

	public void keyTyped(KeyEvent e) {		
	}

	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();	// Returns number of key pressed.
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
	
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		
		// DEBUG
		if(code == KeyEvent.VK_T) {
			if(checkDrawTime == false) {
				checkDrawTime = true;
			}
			else if(checkDrawTime == true) {
				checkDrawTime = false;
			}
		}
			
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
	
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
			
	}
	
}
