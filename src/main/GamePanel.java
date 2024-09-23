package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

// Subclass of 'JPanel'.
public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile. Standard tile size of retro games. 
	final int scale = 3;	// Scales the tiles by 3. It is the standard for making retro games.
	
	public final int tileSize = originalTileSize * scale;	// 48x48 tile size.
	
	// Limits and determines max screen size.
	public final int maxScreenRow = 16;
	public final int maxScreenColumn = 12;
	public final int screenWidth = tileSize * maxScreenRow;	// 768 pixels
	public final int screenHeight = tileSize * maxScreenColumn;	// 576 pixels
	
	// WORLD SETTINGS	
	public final int maxWorldRow = 50;
	public final int maxWorldCol = 50;
	
	// FPS
	int FPS = 60;
	
	// Manages the background tiles.
	TileManager tileM = new TileManager(this);
	
	// Handles inputs.
	KeyHandler keyH = new KeyHandler();
	
	// Sound.
	Sound music = new Sound();
	Sound se = new Sound();
	
	// Collision detector.
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);
	
	// Game Clock.
	Thread gameThread;	
	
	// Initiates player object.
	public Player player = new Player(this, keyH);
	
	// Initiate objects.
	public SuperObject obj[] = new SuperObject[10];
	
	
	// Set player's default position.
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	// Constructor for the window.
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));	// Set's the size of the panel.
		this.setBackground(Color.white);	// Set's the background color
		this.setDoubleBuffered(true);	// For rendering performance.
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		
		playMusic(4);
	}
	
	public void startGameThread() {
		
		// Start's the gameThread Thread.
		gameThread = new Thread(this);	// A new thread is made.
		gameThread.start();	// The thread is executed. run is called.
	}

	
	// Sleep game loop
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS;	// We want to draw the screen 60 times per second.
//		double nextDrawTime = System.nanoTime() + drawInterval;	// Telling the system not to draw again until time + interval.
//		
//		// The way this method works is by putting the method to sleep until the next interval period has come,
//		// by which time the method resumes. 
//		while(gameThread != null) {
//						
//			// 1. UPDATE: update information such as character position.
//			update();
//			
//			// 2. DRAW: draw the screen with the updated information. 
//			repaint();	// This is how you call the paintComponenet method.
//			
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();	// This is how much time the system should wait until next update.
//				remainingTime = remainingTime / 1000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				
//				e.printStackTrace();
//			}
//			
//		}
//		
//	}
	
	// Delta game loop
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
//		long timer = 0;
//		int drawCount = 0;
		
		// The way this method works is that - given sleep isn't being used and the fact that the while loop will execute
		// innumerable times before an interval occurs - it will add the time difference between the end of the last loop 
		// and the start of the new loop divided by the drawInterval. Given the difference would be significantly less than 
		// the drawInterval, the amount added to delta will be less than one. But once enough of these intervals are accumulated
		// they will eventually add up to one or more. By that point, the drawInterval would have been met, and the update and
		// repaint methods can be executed. 		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
//			timer += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta >= 1) {
			
				update();
				repaint();
				
				delta--;
//				drawCount++;
			}
			
			// Proof that 60 intervals are occurring every second. 
//			if(timer >= 1000000000) {
//				System.out.println("FPS:" + drawCount);
//				drawCount = 0;
//				timer = 0;
//			}
		}
	}
	
	// Updates game data.
	public void update() {
		
		player.update();
		
	}
	
	// Paints the screen.
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// DEBUG 
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {;
			drawStart = System.nanoTime();
		}
		
		// Type tile before player. The tile is a layer underneath of player.
		tileM.draw(g2);
		
		// OBJECTS
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		player.draw(g2);
		
		// UI
		ui.draw(g2);
		
		// DEBUG
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("DrawTime: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}
		
		g2.dispose();
		
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		
		music.setFile(i + 1);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
	
}
