package com.github.propra13.gruppe43;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.github.propra13.gruppe43.Items.Item;

public class Field {
	
	//Feldtypen
	public final static char FLOOR = '='; 
	public final static char WALL = '#';  
	public final static char ENTRANCE = 'E';
	public final static char EXIT = 'X';
	public final static char TRAP = 'T';
	public final static char OBJECTIVE = 'D';
	public final static char CHECKPOINT = 'C';
	public final static char ACTIVE_CHECKPOINT = 'B';
	
	//Koordinaten des Feldes
	int x;
	int y;
	
	//Level, in dem sich das Feld befindet
	Level level = null;
	
	//Actor, der sich auf dem Feld befindet
	Actor actor = null;
	
	//Items, die sich auf dem Feld befinden
	public ArrayList<Item> items = null;
	
	//Attribute
	char type;
	boolean walkable = true;
	
	
	//erstellt ein Feld mit dem angegebenen Typen im Level level
	public Field(char tp, Level lvl) {
		level = lvl;
		this.changeType(tp);
	
	}
		
	//ändert den Typen des Feldes
	public void changeType(char tp) {
		items = new ArrayList<Item>();
		type = tp;
		switch (type) {
		case FLOOR:
			walkable = true;
			break;
		case WALL:
			walkable = false;
			break;
		case TRAP:
			walkable = true;
			break;
		case ENTRANCE:
			level.entrance = this;
			walkable = true;
			break;
		case EXIT:
			level.exit = this;
			walkable = true;
			break;
		case OBJECTIVE:
			walkable = true;
			break;
		case CHECKPOINT:
			walkable = true;
			break;
		case ACTIVE_CHECKPOINT:
			walkable = true;
			break;
		default:
			walkable = true;
			break;
		}
	}
		
		
		
	//wird aufgerufen, wenn dieses Feld betreten wird
	public void onEntry() {
		actor.getInventory().pickupItems(this);
		switch (type) {
		case TRAP:
			actor.takeDamage(null, 30, DamageTypes.PHYSICAL);
			break;
		case ENTRANCE:
			if (this.actor == this.level.game.player) level.game.changeLevel(level.game.currentLevelId-1, 1);
			break;
		case EXIT:
			if (this.actor == this.level.game.player) level.game.changeLevel(level.game.currentLevelId+1, 0);
			break;
		case OBJECTIVE:
			if (this.actor == this.level.game.player) level.game.state = 2;
			break;
		case CHECKPOINT:
			if (this.actor == this.level.game.player) level.game.setCheckpoint(this);
			break;
		default:
			break;
		}
		
	}
	
	
	//
	public void addItem(Item item) {
		items.add(item);
	}
	
	//ändert Koordinaten des Feldes
	public void setxy(int set_x, int set_y) {
		x = set_x;
		y = set_y;
	}
	
	
	//befindet sich ein Actor auf diesem Feld?
	public boolean isOccupied() {
		if (actor == null) {
			return false;
		}
		else {
			return true;
		}
	}
	//ist dieses Feld begehbar?
	public boolean isWalkable() {
		return walkable;
	}
	
	public ArrayList<Item> getItems() { return this.items; }
	public Actor getActor() { return this.actor; }
	public Level getLevel() { return this.level; }
	public Game getGame() {return this.level.game; }
	
	
	
	
	

}
