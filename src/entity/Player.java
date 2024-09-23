package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		// Places player at the center of the screen. The subtraction is because the character would be 
		// centered based off the top left corner of his model square.
		screenX = gp.screenWidth/2 - (gp.tileSize / 2);
		screenY = gp.screenHeight/2 - (gp.tileSize / 2);
		
		// Collision area is determined. These can also be written within the parenthesis.
		solidArea = new Rectangle();
		solidArea.x = 14;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 20;
		solidArea.height = 28;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		
		up0 = setup("MC_BNeutral");
		up1 = setup("MC_LBstride");
		up2 = setup("MC_RBstride");
		down0 = setup("MC_FNeutral");
		down1 = setup("MC_LFstride");
		down2 = setup("MC_RFstride");
		left0 = setup("MC_LPneutral");
		left1 = setup("MC_LPstride");
		right0 = setup("MC_RPneutral");
		right1 = setup("MC_RPstride");
	}
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize); 
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true) {
						
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			} 
			
			// CHECK TILE COLLISION.
			collisionOn = false; 
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION 
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE.
			if(collisionOn == false) {
				
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 10 || initialMovement == true) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum != 1) {
					spriteNum = 1;
				}
				initialMovement = false;
				spriteCounter = 0;
			}
		}
		else {
			initialMovement = true;
			spriteNum = 0;
		}
	}
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("You opened the door!");
				}
				else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speed += 2;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(2);
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		// To create a white rectangle.
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		// To load a set of images.
		BufferedImage image = null;
		image = up1;
		
		switch(direction) {
		case "up":
			if(spriteNum == 0) {
				image = up0;
			}
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 0) {
				image = down0;
			}
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 0 || spriteNum == 2) {
				image = left0;
			}
			if(spriteNum == 1) {
				image = left1;
			}
			break;
		case "right":
			if(spriteNum == 0 || spriteNum == 2) {
				image = right0;
			}
			if(spriteNum == 1) {
				image = right1;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
		
		
	}
}
