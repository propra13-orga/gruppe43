package com.github.propra13.gruppe43;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameDisplay {
	static int WINDOW_SIZE_X = 550;
	static int WINDOW_SIZE_Y = 400;
	static int TILE_WIDTH = 32;
	static int TILE_HEIGHT = 32;
	
	//Bild, das angezeigt wird
	BufferedImage display;
	
	//Spiel, das angezeigt wird
	Game game;
	
	//Actor, dem das Bild folgt
	Actor focus;
	
	//Bilder für Fields, Actors, etc
	final int FIELD_IMAGE_COUNT = 6;
	final int ACTOR_IMAGE_COUNT = 2;
	BufferedImage[] fieldImages = new BufferedImage[FIELD_IMAGE_COUNT];
	BufferedImage[] actorImages = new BufferedImage[ACTOR_IMAGE_COUNT];
	String[] fieldPath = {"FLOOR.png","WALL.png", "TRAP.png", "ENTRANCE.png", "EXIT.png","OBJECTIVE.png"};
	String[] actorPath = {"player.png", "ENEMY.png"};
	
	public GameDisplay(Game gm) {
		game = gm;
		focus = game.player;
		display = new BufferedImage(WINDOW_SIZE_X, WINDOW_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
		
	}
	//lädt alle Bilder
	public void init() {
		try {
			for (int i = 0; i<FIELD_IMAGE_COUNT;i++) fieldImages[i] = ImageIO.read(new File("img/"+fieldPath[i]));
			for (int i = 0; i<ACTOR_IMAGE_COUNT;i++) actorImages[i] = ImageIO.read(new File("img/"+actorPath[i]));
			
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//zeichnet das gesamte Bild, das angezeigt werden soll
	public void updateDisplay() {
		int width = game.getCurrentLevel().size_x*32;
		int height = game.getCurrentLevel().size_y*32;
		int focusX = focus.getField().x*32+16;
		int focusY = focus.getField().y*32+16;
		int displayX;
		int displayY;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		drawFields(g, game.getCurrentLevel());
		drawActors(g, game.getCurrentLevel());
		displayX = Math.max(0, Math.min(focusX-(WINDOW_SIZE_X/2), width-WINDOW_SIZE_X));
		displayY = Math.max(0, Math.min(focusY-(WINDOW_SIZE_Y/2), height-WINDOW_SIZE_Y));
		display = img.getSubimage(displayX, displayY, Math.min(WINDOW_SIZE_X, width-displayX), Math.min(WINDOW_SIZE_Y, height-displayY));
	}
	
	//Gibt das Bild zurück
	public BufferedImage getDisplay() { return display; }
	

	
	//zeichnet Fields
	private void drawFields(Graphics2D g, Level level) {
		BufferedImage img;
		for (int i=0; i<level.size_x;i++) {
			for (int j=0; j<level.size_y;j++) {
				switch (level.getField(i, j).type) {
				case Field.FLOOR:
					img = fieldImages[0];
					break;
				case Field.WALL:
					img = fieldImages[1];
					break;
				case Field.TRAP:
					img = fieldImages[2];
					break;
				case Field.ENTRANCE:
					img = fieldImages[3];
					break;
				case Field.EXIT:
					img = fieldImages[4];
					break;
				case Field.OBJECTIVE:
					img = fieldImages[5];
					break;
				default:
					img = fieldImages[0];
					break;
				}
				g.drawImage(img, i*TILE_WIDTH, j*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
			}
		}
		
	}
	
	
	//Zeichnet Actors
	private void drawActors(Graphics2D g, Level level) {
		BufferedImage img;
		Actor a;
		for (int j=0; j<level.actors.size();j++) {
			a = level.actors.get(j);
			switch (a.type) {
			case Actor.PLAYER:
				img = actorImages[0];
				break;
			case 1:
				img = actorImages[1];
				break;
			default:
				img = actorImages[0];
				break;
			}
			g.drawImage(img, a.field.x*TILE_WIDTH, a.field.y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
		}
	}
	
	
	


	

}
