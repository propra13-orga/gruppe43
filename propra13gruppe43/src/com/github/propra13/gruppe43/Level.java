package com.github.propra13.gruppe43;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import com.github.propra13.gruppe43.Items.Item;

import Effects.Effect;


public class Level {
	//ID des Levels im Spiel
	int id;
	//Größe des Levels
	int size_x, size_y;
	//Felder des Levels
	ArrayList<ArrayList<Field>> fields;
	//Actors des Levels
	ArrayList<Actor> actors;
	//Effekte  des Levels
	ArrayList<Effect> effects;
	//Projektile de levels
	ArrayList<Projectile> projectiles;
	//Game zu dem dieses Level gehört
	Game game = null;
	//Ein- und Ausgang des Levels
	Field entrance = null;
	Field exit = null;
	Boss boss = null;
	
	
	public Level(int i) {
		id = i;
		fields = new ArrayList<ArrayList<Field>>();
		actors = new ArrayList<Actor>();
		effects = new ArrayList<Effect>();
		projectiles = new ArrayList<Projectile>();
	}
	
	
	//liest ein Level aus einer Textdatei mit dem Pfad "path" ein und speichert dieses in dem Array fields
	public boolean init(String path, Game gm) {
		game = gm;
		String s;
		int c = 0;
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File(path));
			do {
				s = scanner.nextLine();
				for (int i = 0; i<s.length(); i++) {
					if (c==0) fields.add(new ArrayList<Field>());
					
					fields.get(i).add(new Field(s.charAt(i), this));
					fields.get(i).get(c).setxy(i, c);
					spawnObject(fields.get(i).get(c), s.charAt(i));
				}
				c++;		

			} while (scanner.hasNextLine());
			size_x = fields.size();
			size_y = fields.get(0).size();
			
			if (boss != null) {
				entrance.changeType(Field.FLOOR);
				exit.changeType(Field.FLOOR);
			}
			
			return true;
			
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			return false;
		} finally {
			scanner.close();
		}
		
		

	}
	//erzeugt ein Actor oder ein Item auf dem Zielfeld, abhängig von s:
	public void spawnObject(Field t, char s) {
		switch (s) {
		case 'a':
			String[] text = {"Es ist gefährlich zu gehen alleine.", "Nimm dies.", " ", "(Benutze 'A' und die Pfeiltasten um anzugreifen.)", "(Benutze SPACE um diesen Dialog zu beenden.)"};
			TextNPC guy = new TextNPC("Hans",text);
			guy.move(t, true);
			break;
		//Level 2
		case 'b':
			Enemy boy = new Enemy(Actor.ENEMY, new PatrolAI(game.player, 1, 0), 50, 50, 3, 1);
			boy.move(t, false);
			boy.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
			break;
		case 'c':
			boy = new Enemy(Actor.ENEMY, new PatrolAI(game.player, 1, 0), 50, 50, 3, 1);
			boy.move(t, false);
			boy.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
			boy.getInventory().items.add(Item.createItem(Item.ID_HEALTH_POTION));
			break;
		case 'd':
			ShopNPC man = new ShopNPC("Verkäufer"); 
			man.getInventory().items.add(Item.createItem(Item.ID_AXE));
			man.getInventory().items.add(Item.createItem(Item.ID_TRASH_ARMOR));
			man.move(t, true);
			break;
		case 'e':
			boy = new Enemy(Actor.ENEMY, new PatrolAI(game.player, 0, 1), 75, 50, 5, 1);
			boy.move(t, false);
			boy.getInventory().equipItem(Item.createItem(Item.ID_AXE));
			break;
		case 'f':
			man = new ShopNPC("Verkäufer"); 
			man.getInventory().items.add(Item.createItem(Item.ID_VICTORY_ARMOR));
			man.getInventory().setDrop(1);
			man.move(t, true);
			break;
			
		case 'o':
			boss = new Boss( new FindAI(game.player), 150, 0, 3, 1);
			boss.getInventory().changeGold(200);
			boss.move(t, false);
			boss.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
			break;
		case 'p':
			boss = new Boss( new FindAI(game.player), 200, 0, 5, 1);
			boss.getInventory().changeGold(300);
			boss.move(t, false);
			boss.getInventory().equipItem(Item.createItem(Item.ID_AXE));
			break;
		case 'q':
			boss = new Boss( new FindAI(game.player), 300, 0, 8, 1);
			boss.getInventory().changeGold(9000);
			boss.move(t, false);
			boss.getInventory().equipItem(Item.createItem(Item.ID_AXE));
			break;
			
		case '0':
			t.addItem(Item.createItem(Item.ID_SWORD));
			break;
		case '1':
			t.addItem(Item.createItem(Item.ID_SPELLBOOK_FIREBALL));
			break;
		case '2':
			t.addItem(Item.createItem(Item.ID_MANA_POTION));
			break;
		default:
			break;
		}
		
		
	}
	// gibt das Feld des Levels mit den Koordinaten x,y zurück
	public Field getField(int x, int y) {
		if (x<size_x && y<size_y && x>=0 && y>=0) {
			return fields.get(x).get(y);
		}
		else {
			return null;
		}
	}
	public int getId() {return id;}
	public void addActor(Actor actor) { actors.add(actor); }
	public void removeActor(Actor actor) { actors.remove(actor); }
	public void addEffect(Effect effect) { effects.add(effect); }
	public void removeEffect(Effect effect) { effects.remove(effect); }
	public void addProjectile(Projectile projectile) { projectiles.add(projectile); }
	public void removeProjectile(Projectile projectile) { projectiles.remove(projectile); }

}
