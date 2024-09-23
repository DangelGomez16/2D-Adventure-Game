package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp; 
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		
		setup(0, "Grass_Tile", false);
		setup(1, "Brick_Tile", true);
		setup(2, "Water_Tile", true);
		setup(3, "Dirt_Tile", false);
		setup(4, "Tree_Tile", true);
		setup(5, "Sand_Tile", false);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(row < gp.maxWorldRow) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[row]);
					
					mapTileNum[row][col] = num;
					row++;
				}
				
				if(row == gp.maxWorldRow) {
					row = 0;
					col++;
				}
			}
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldRow][worldCol];
			
			int worldX = worldRow * gp.tileSize;
			int worldY = worldCol * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			

				
			g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			
			worldRow++;
			
			if(worldRow == gp.maxWorldRow) {
				worldRow = 0;
				worldCol++;
			}
		}

	}
}
