package com.github.propra13.gruppe43;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LevelPanel extends JPanel{
	final int IMAGE_COUNT = 4;
	Game game = null;
	BufferedImage img;
	BufferedImage[] images = new BufferedImage[IMAGE_COUNT];
	String[] path = {"FLOOR.png","WALL.png", "ENTRANCE.png", "EXIT.png"};
	BufferedImage playerimg = null;
	
	public void init (){
		String[] lvlpath = {"level1.txt", "level2.txt", "level3.txt", "level4.txt"};
		game = new Game(lvlpath);
		try {
			for (int i = 0; i<IMAGE_COUNT;i++) images[i] = ImageIO.read(new File(path[i]));
			playerimg = ImageIO.read(new File("player.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void paint(Graphics g) {
			int width = 32;
			int height = 32;
			for (int i=0; i<game.levels[game.currentLevelId].size_x;i++) {
				for (int j=0; j<game.levels[game.currentLevelId].size_y;j++) {
					switch (game.levels[game.currentLevelId].getField(i, j).type) {
					case Field.FLOOR:
						img = images[0];
						break;
					case Field.WALL:
						img = images[1];
						break;
					case Field.ENTRANCE:
						img = images[2];
						break;
					case Field.EXIT:
						img = images[3];
						break;
					default:
						img = images[0];
						break;
					}
					g.drawImage(img, i*width, j*height, width, height, null);
					if (game.levels[game.currentLevelId].getField(i, j).actor != null) {
						g.drawImage(playerimg, i*width, j*height, width, height, null);
					}
				}
			}
		}
		


}