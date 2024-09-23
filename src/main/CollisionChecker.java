package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;	
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftRow = entityLeftWorldX / gp.tileSize;
		int entityRightRow = entityRightWorldX / gp.tileSize;
		int entityTopCol = entityTopWorldY / gp.tileSize;
		int entityBottomCol = entityBottomWorldY / gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopCol = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftRow][entityTopCol];
			tileNum2 = gp.tileM.mapTileNum[entityRightRow][entityTopCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomCol = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftRow][entityBottomCol];
			tileNum2 = gp.tileM.mapTileNum[entityRightRow][entityBottomCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftRow = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftRow][entityTopCol];
			tileNum2 = gp.tileM.mapTileNum[entityLeftRow][entityBottomCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightRow = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightRow][entityTopCol];
			tileNum2 = gp.tileM.mapTileNum[entityRightRow][entityBottomCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
		
	}
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			
			if(gp.obj[i] != null) {
				
				// Get entity's solid area position.
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// Get object's solid area position.
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

				
			}
		}
		
		return index;
	}
}
