package com.github.propra13.gruppe43;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Effects.Effect;

import com.github.propra13.gruppe43.Dialogs.Dialog;
import com.github.propra13.gruppe43.Dialogs.InventoryDialog;
import com.github.propra13.gruppe43.Dialogs.MainMenuDialog;
import com.github.propra13.gruppe43.Dialogs.ShopDialog;
import com.github.propra13.gruppe43.Dialogs.TextDialog;
import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;

public class GameDisplay {
	public final static int TILE_WIDTH = 32;
	public final static int TILE_HEIGHT = 32;
	public final static int BLOCKS_X = 16;
	public final static int BLOCKS_Y = 12;
	public final static int WINDOW_SIZE_X = BLOCKS_X*TILE_WIDTH;
	public final static int WINDOW_SIZE_Y = BLOCKS_Y*TILE_HEIGHT;
	
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
	
	final int ACTOR_IMAGE_COUNT = 5;
	BufferedImage[] actorImages = new BufferedImage[ACTOR_IMAGE_COUNT];
	String[] actorPath = {"PLAYER.png", "ENEMY.png", "TEXT_NPC.png", "SHOP_NPC.png", "BOSS.png"};
	
	final int ITEM_IMAGE_COUNT = 8;
	BufferedImage[] itemImages = new BufferedImage[ITEM_IMAGE_COUNT];
	BufferedImage[] actorItemImages = new BufferedImage[ITEM_IMAGE_COUNT];
	String[] itemPath = {"SWORD.png", "TRASH_ARMOR.png", "HEALTH_POTION.png","MANA_POTION.png","GOLD.png", "SPELLBOOK_FIREBALL.png", "AXE.png", "VICTORY_ARMOR.png"};
	String[] actorItemPath = {"ACTOR_SWORD.png", "ACTOR_TRASH_ARMOR.png", "HEALTH_POTION.png","MANA_POTION.png","GOLD.png", "SPELLBOOK_FIREBALL.png", "ACTOR_AXE.png", "ACTOR_VICTORY_ARMOR.png"};	
	
	final int PROJECTILE_IMAGE_COUNT = 1;
	BufferedImage[] projectileImages = new BufferedImage[PROJECTILE_IMAGE_COUNT];
	String[] projectilePath = {"FIREBALL.png"};
	
	final int EFFECT_IMAGE_COUNT = 2;
	BufferedImage[] effectImages = new BufferedImage[EFFECT_IMAGE_COUNT];
	String[] effectPath = {"SLASH.png", "FIRE_SPLASH.png"};
	
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
			for (int i = 0; i<PROJECTILE_IMAGE_COUNT;i++) projectileImages[i] = ImageIO.read(new File("img/"+projectilePath[i]));
			
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//zeichnet das gesamte Bild, das angezeigt werden soll
	public void updateDisplay() {
		Graphics2D displayG;
		displayG = (Graphics2D) display.getGraphics();
		
		if (game.state != Game.PAUSED) {
			
			focus = game.player;
			
			int width = game.getCurrentLevel().size_x*TILE_WIDTH;
			int height = game.getCurrentLevel().size_y*TILE_HEIGHT;
			int focusX = (int) ((focus.getField().x-focus.calcOffsetx())*TILE_WIDTH+16);
			int focusY = (int) ((focus.getField().y-focus.calcOffsety())*TILE_HEIGHT+16);
			int displayX;
			int displayY;
			
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) img.getGraphics();
			drawFields(g, game.getCurrentLevel());
			drawActors(g, game.getCurrentLevel());
			drawProjectiles(g, game.getCurrentLevel());
			drawEffects(g, game.getCurrentLevel());
			
			displayX = Math.max(0, Math.min(focusX-(WINDOW_SIZE_X/2), width-WINDOW_SIZE_X));
			displayY = Math.max(0, Math.min(focusY-(WINDOW_SIZE_Y/2), height-WINDOW_SIZE_Y));
			display = new BufferedImage(WINDOW_SIZE_X, WINDOW_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
			displayG = (Graphics2D) display.getGraphics();
			displayG.drawImage(img.getSubimage(displayX, displayY, Math.min(WINDOW_SIZE_X, width-displayX), Math.min(WINDOW_SIZE_Y, height-displayY)), 0, 0, null);
			drawUI(displayG);
		}
		
		if (game.dialogActive) drawDialog(displayG, game.dialog);
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
			int offx = (int) ((a.getField().x-a.calcOffsetx())*TILE_WIDTH);
			int offy = (int) ((a.getField().y-a.calcOffsety())*TILE_HEIGHT);
			g.drawImage(img, offx, offy, TILE_WIDTH, TILE_HEIGHT, null);
			
			for (int k=0; k<Inventory.EQSLOTS; k++) {
				if (a.getInventory().getEquipment(k) != null) {
					img = actorItemImages[a.getInventory().getEquipment(k).getId()];
					g.drawImage(img, offx, offy, TILE_WIDTH, TILE_HEIGHT, null);
				}
			}
			
			if (a.getHealth() != a.getMaxHealth() && a != game.player) {
				g.setStroke(new BasicStroke(1));
				g.setColor(new Color(255, 255, 255));
				g.fillRect(offx, offy, TILE_WIDTH, TILE_HEIGHT/10); 
				g.setColor(new Color(200, 0, 0));
				g.fillRect(offx, offy, (int) (TILE_WIDTH*(a.getHealth()/a.getMaxHealth())), TILE_HEIGHT/10); 
				g.setColor(new Color(0, 0, 0));
				g.drawRect(offx, offy, TILE_WIDTH, TILE_HEIGHT/10); 
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
	
	//Zeichnet Projektile
		private void drawProjectiles(Graphics2D g, Level level) {
			BufferedImage img;
			Projectile p;		
			for (int j=0; j<level.projectiles.size();j++) {

				p = level.projectiles.get(j);
				int offx = (int) ((p.getField().x-p.calcOffsetx())*TILE_WIDTH);
				int offy = (int) ((p.getField().y-p.calcOffsety())*TILE_HEIGHT);
				img = projectileImages[p.type];
				img = img.getSubimage(32*(1+p.facex), 32*(1+p.facey), 32, 32);
				g.drawImage(img, offx, offy, TILE_WIDTH, TILE_HEIGHT, null);
			}
		}
	
	
	
	private void drawUI(Graphics2D g) {
		//UI-Leiste zeichnen
		g.setColor(new Color(210, 105, 30));
		g.fillRect(TILE_WIDTH, WINDOW_SIZE_Y-TILE_HEIGHT, (BLOCKS_X-2)*TILE_WIDTH, TILE_HEIGHT); 
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(3));
		g.drawRect(TILE_WIDTH, WINDOW_SIZE_Y-TILE_HEIGHT,  (BLOCKS_X-2)*TILE_WIDTH, TILE_HEIGHT); 
		//Lebensleiste zeichnen
		g.setStroke(new BasicStroke(2));
		g.setColor(new Color(255, 255, 255));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/8)*7, TILE_WIDTH*(BLOCKS_X/4), TILE_HEIGHT/3); 
		g.setColor(new Color(200, 0, 0));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/8)*7,(int) (TILE_WIDTH*(BLOCKS_X/4)*(focus.getHealth()/focus.getMaxHealth())), TILE_HEIGHT/3); 
		g.setColor(new Color(0, 0, 0));
		g.drawRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/8)*7, TILE_WIDTH*(BLOCKS_X/4), TILE_HEIGHT/3); 
		//Manaleiste zeichnen
		g.setColor(new Color(255, 255, 255));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/16)*7, TILE_WIDTH*(BLOCKS_X/4), TILE_HEIGHT/3); 
		g.setColor(new Color(0, 100, 255));
		g.fillRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/16)*7,(int) (TILE_WIDTH*(BLOCKS_X/4)*(focus.getMana()/focus.getMaxMana())), TILE_HEIGHT/3);  
		g.setColor(new Color(0, 0, 0));
		g.drawRect(TILE_WIDTH+5, WINDOW_SIZE_Y-(TILE_HEIGHT/16)*7, TILE_WIDTH*(BLOCKS_X/4), TILE_HEIGHT/3); 
		//Leben
		g.setFont(new Font("Arial", Font.BOLD, (TILE_HEIGHT*2)/5));
		g.drawString(("Leben: " + game.getLives()), (TILE_WIDTH*(BLOCKS_X/4)+TILE_WIDTH+10), WINDOW_SIZE_Y-(TILE_HEIGHT*7)/8+TILE_HEIGHT/3);		
		g.drawString(("Gold: " + game.player.getInventory().getGold()), (TILE_WIDTH*(BLOCKS_X/4)+TILE_WIDTH+10), WINDOW_SIZE_Y-(TILE_HEIGHT*7)/16+TILE_HEIGHT/3);		
		
		
	}
	
	
	private void drawDialog(Graphics2D g, Dialog dialog) {
		if (dialog.type == Dialog.TEXT) {
			int offsetx = (int) (WINDOW_SIZE_X*0.1);
			int offsety = (int) (WINDOW_SIZE_Y*0.1);
			TextDialog d = (TextDialog) dialog;
			g.setColor(new Color(255, 255, 255));
			g.fillRect(offsetx, offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setStroke(new BasicStroke(3));
			g.setColor(new Color(0, 0, 0));
			g.drawRect((int) offsetx, (int) offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setFont(new Font("Arial", Font.BOLD, 15));
			offsetx+=30;
			offsety+=30;
			g.drawString((d.headline), offsetx, offsety);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			for (int i = 0; i<d.text.length; i++) {
				offsety+=20;
				g.drawString((d.text[i]), offsetx, offsety);
				

				
			}
			
		}

		if (dialog.type == Dialog.SHOP) {
			int offsetx = (int) (WINDOW_SIZE_X*0.1);
			int offsety = (int) (WINDOW_SIZE_Y*0.1);
			ShopDialog d = (ShopDialog) dialog;
			g.setColor(new Color(255, 255, 255));
			g.fillRect(offsetx, offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setStroke(new BasicStroke(3));
			g.setColor(new Color(0, 0, 0));
			g.drawRect((int) offsetx, (int) offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setFont(new Font("Arial", Font.BOLD, 15));
			offsetx+=30;
			offsety+=30;
			g.drawString((d.headline), offsetx, offsety);
			g.drawString("Gold:", offsetx+200, offsety);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			for (int i = 0; i<=d.inventory.items.size(); i++) {
				offsety+=20;
				if (i==d.selectionIndex) g.drawString("->", offsetx-20, offsety);
				if (i<d.inventory.items.size()) {
				g.drawString(d.inventory.items.get(i).getName(), offsetx, offsety);
				g.drawString(String.valueOf(d.inventory.items.get(i).getCost()), offsetx+200, offsety);
				}
				else g.drawString("Shop verlassen", offsetx, offsety);
							
			}
			
		}
		
		if (dialog.type == Dialog.INVENTORY) {
			int offsetx = (int) (WINDOW_SIZE_X*0.1);
			int offsety = (int) (WINDOW_SIZE_Y*0.1);
			InventoryDialog d = (InventoryDialog) dialog;
			g.setColor(new Color(255, 255, 255));
			g.fillRect(offsetx, offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setStroke(new BasicStroke(3));
			g.setColor(new Color(0, 0, 0));
			g.drawRect((int) offsetx, (int) offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setFont(new Font("Arial", Font.BOLD, 15));
			offsetx+=30;
			offsety+=30;
			g.drawString((d.headline), offsetx, offsety);
			
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Waffe:", offsetx+200, offsety);
			g.drawString("Rüstung:", offsetx+200, offsety+40);
			
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			if (d.inventory.hasEquipped(Item.TYPE_WEAPON)) 
				g.drawString(d.inventory.getEquipment(Item.TYPE_WEAPON).getName(), offsetx+200, offsety+20);
			else 
				g.drawString("---", offsetx+200, offsety+20);
			if (d.inventory.hasEquipped(Item.TYPE_ARMOR)) 
				g.drawString(d.inventory.getEquipment(Item.TYPE_ARMOR).getName(), offsetx+200, offsety+60);
			else 
				g.drawString("---", offsetx+200, offsety+60);
			
			
			
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			for (int i = 0; i<=d.inventory.items.size(); i++) {
				offsety+=20;
				if (i==d.selectionIndex) g.drawString("->", offsetx-20, offsety);
				if (i<d.inventory.items.size()) {
				g.drawString(d.inventory.items.get(i).getName(), offsetx, offsety);
				}
				else g.drawString("Inventar schließen", offsetx, offsety);
							
			}
			
		}
		
		if (dialog.type == Dialog.MAIN_MENU) {
			int offsetx = (int) (WINDOW_SIZE_X*0.1);
			int offsety = (int) (WINDOW_SIZE_Y*0.1);
			MainMenuDialog d = (MainMenuDialog) dialog;
			g.setColor(new Color(255, 255, 255));
			g.fillRect(offsetx, offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setStroke(new BasicStroke(3));
			g.setColor(new Color(0, 0, 0));
			g.drawRect((int) offsetx, (int) offsety, (int) (WINDOW_SIZE_X*0.8), (int) (WINDOW_SIZE_Y*0.8));
			g.setFont(new Font("Arial", Font.BOLD, 15));
			offsetx+=30;
			offsety+=30;
			g.drawString((d.headline), offsetx, offsety);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			for (int i = 0; i<d.options.length; i++) {
				offsety+=20;
				if (i==d.selectionIndex) g.drawString("->", offsetx-20, offsety);
				g.drawString(d.options[i], offsetx, offsety);
							
			}
			
		}
	}
	
	
	


	

}
