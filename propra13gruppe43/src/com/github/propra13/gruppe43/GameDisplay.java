package com.github.propra13.gruppe43;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Effects.Effect;

import com.github.propra13.gruppe43.Items.Inventory;

public class GameDisplay {
	static int WINDOW_SIZE_X = 520;
	static int WINDOW_SIZE_Y = 390;
	static int TILE_WIDTH = 32;
	static int TILE_HEIGHT = 32;
	
	//Bild, das angezeigt wird
	BufferedImage display;
	
	//Spiel, das angezeigt wird
	Game game;
	
	//Actor, dem das Bild folgt
	Actor focus;
	
	//Bilder für Fields, Actors, etc
	final int FIELD_IMAGE_COUNT = 8;
	BufferedImage[] fieldImages = new BufferedImage[FIELD_IMAGE_COUNT];
	String[] fieldPath = {"FLOOR.png","WALL.png", "TRAP.png", "ENTRANCE.png", "EXIT.png","OBJECTIVE.png", "CHECKPOINT.png","ACTIVE_CHECKPOINT.png"};
	
	final int ACTOR_IMAGE_COUNT = 2;
	BufferedImage[] actorImages = new BufferedImage[ACTOR_IMAGE_COUNT];
	String[] actorPath = {"PLAYER.png", "ENEMY.png"};
	
	final int ITEM_IMAGE_COUNT = 2;
	BufferedImage[] itemImages = new BufferedImage[ITEM_IMAGE_COUNT];
	BufferedImage[] actorItemImages = new BufferedImage[ITEM_IMAGE_COUNT];
	String[] itemPath = {"SWORD.png", "TRASH_ARMOR.png"};
	String[] actorItemPath = {"ACTOR_SWORD.png", "ACTOR_TRASH_ARMOR.png"};
	
	final int EFFECT_IMAGE_COUNT = 1;
	BufferedImage[] effectImages = new BufferedImage[EFFECT_IMAGE_COUNT];
	String[] effectPath = {"SLASH.png"};
	
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
			for (int i = 0; i<ITEM_IMAGE_COUNT;i++) itemImages[i] = ImageIO.read(new File("img/"+itemPath[i]));
			for (int i = 0; i<ITEM_IMAGE_COUNT;i++) actorItemImages[i] = ImageIO.read(new File("img/"+actorItemPath[i]));
			for (int i = 0; i<EFFECT_IMAGE_COUNT;i++) effectImages[i] = ImageIO.read(new File("img/"+effectPath[i]));
			
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//zeichnet das gesamte Bild, das angezeigt werden soll
	public void updateDisplay() {
		int width = game.getCurrentLevel().size_x*TILE_WIDTH;
		int height = game.getCurrentLevel().size_y*TILE_HEIGHT;
		int focusX = focus.getField().x*TILE_WIDTH+16;
		int focusY = focus.getField().y*TILE_HEIGHT+16;
		int displayX;
		int displayY;
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		drawFields(g, game.getCurrentLevel());
		drawActors(g, game.getCurrentLevel());
		drawEffects(g, game.getCurrentLevel());
		
		displayX = Math.max(0, Math.min(focusX-(WINDOW_SIZE_X/2), width-WINDOW_SIZE_X));
		displayY = Math.max(0, Math.min(focusY-(WINDOW_SIZE_Y/2), height-WINDOW_SIZE_Y));
		display = img.getSubimage(displayX, displayY, Math.min(WINDOW_SIZE_X, width-displayX), Math.min(WINDOW_SIZE_Y, height-displayY));
		g = (Graphics2D) display.getGraphics();
		drawUI(g);
	}
	
	//Gibt das Bild zurück
	public BufferedImage getDisplay() { return display; }
	

	
	//zeichnet Fields
	private void drawFields(Graphics2D g, Level level) {
		BufferedImage img;
		Field f;
		int posx;
		int posy;
		for (int i=0; i<level.size_x;i++) {
			posx = i*TILE_WIDTH;
			for (int j=0; j<level.size_y;j++) {
				posy = j*TILE_HEIGHT;
				f = level.getField(i, j);
				switch (f.type) {
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
				case Field.CHECKPOINT:
					img = fieldImages[6];
					break;
				case Field.ACTIVE_CHECKPOINT:
					img = fieldImages[7];
					break;
				default:
					img = fieldImages[0];
					break;
				}
				g.drawImage(img, posx, posy, TILE_WIDTH, TILE_HEIGHT, null);
				for (int k=0; k<f.getItems().size();k++) {
					img = itemImages[f.getItems().get(k).getId()];
					g.drawImage(img, posx, posy, TILE_WIDTH, TILE_HEIGHT, null);
				}
			}
		}
		
	}
	
	
	//Zeichnet Actors
	private void drawActors(Graphics2D g, Level level) {
		BufferedImage img;
		Actor a;
		for (int j=0; j<level.actors.size();j++) {
			a = level.actors.get(j);
			img = actorImages[a.type];
			g.drawImage(img, a.field.x*TILE_WIDTH, a.field.y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
			for (int k=0; k<Inventory.EQSLOTS; k++) {
				if (a.getInventory().getEquipment(k) != null) {
					img = actorItemImages[a.getInventory().getEquipment(k).getId()];
					g.drawImage(img, a.getField().x*TILE_WIDTH, a.getField().y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
				}
			}
		}
	}
	

	//Zeichnet Effekte
	private void drawEffects(Graphics2D g, Level level) {
		BufferedImage img;
		Effect e;
		for (int j=0; j<level.effects.size();j++) {
			e = level.effects.get(j);
			img = effectImages[e.type];
			img = img.getSubimage(32*e.state, 0, 32, 32);
			g.drawImage(img, e.target.x*TILE_WIDTH, e.target.y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
		}
	}
	
	
	
	private void drawUI(Graphics2D g) {
		//UI-Leiste zeichnen
		g.setColor(new Color(210, 105, 30));
		g.fillRect(TILE_WIDTH, WINDOW_SIZE_Y-TILE_HEIGHT,(int) (WINDOW_SIZE_X*0.8), TILE_HEIGHT); 
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(3));
		g.drawRect(TILE_WIDTH, WINDOW_SIZE_Y-TILE_HEIGHT,(int) (WINDOW_SIZE_X*0.8), TILE_HEIGHT); 
		//Lebensleiste zeichnen
		g.setStroke(new BasicStroke(2));
		g.setColor(new Color(255, 255, 255));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/8)*7,(int) (WINDOW_SIZE_X*0.2), TILE_HEIGHT/3); 
		g.setColor(new Color(200, 0, 0));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/8)*7,(int) (WINDOW_SIZE_X*((double) focus.getHealth()/focus.getMaxHealth())*0.2), TILE_HEIGHT/3); 
		g.setColor(new Color(0, 0, 0));
		g.drawRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/8)*7,(int) (WINDOW_SIZE_X*0.2), TILE_HEIGHT/3); 
		//Manaleiste zeichnen
		g.setColor(new Color(255, 255, 255));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/16)*7,(int) (WINDOW_SIZE_X*0.2), TILE_HEIGHT/3); 
		g.setColor(new Color(0, 100, 255));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/16)*7,(int) (WINDOW_SIZE_X*((double) focus.getMana()/focus.getMaxMana())*0.2), TILE_HEIGHT/3);  
		g.setColor(new Color(0, 0, 0));
		g.drawRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/16)*7,(int) (WINDOW_SIZE_X*0.2), TILE_HEIGHT/3); 
		//Leben
		g.drawString(("Leben:" + game.getLives()), (float) (WINDOW_SIZE_X*0.22+TILE_WIDTH+5), (float) (WINDOW_SIZE_Y-TILE_HEIGHT/2));		
		
		
	}
	
	
	


	

}
