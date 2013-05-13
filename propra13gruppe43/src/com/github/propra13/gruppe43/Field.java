package com.github.propra13.gruppe43;

import java.awt.image.BufferedImage;

public class Field {
	//Feldtypen
	final static char FLOOR = 'O'; 
	final static char WALL = '#';  
	final static char ENTRANCE = 'E';
	final static char EXIT = 'X';
	//Koordinaten des Feldes
	int x;
	int y;
	//Level, in dem sich das Feld befindet
	Level level = null;
	//Actor, der sich auf dem Feld befindet
	Actor actor = null;
	//Attribute
	char type;
	boolean walkable = true;
	
	//erstellt ein Feld mit dem angegebenen Typen im Level level
	public Field(char tp, Level lvl) {
		type = tp;
		level = lvl;
		switch (type) {
		case FLOOR:
			walkable = true;
			break;
		case WALL:
			walkable = false;
			break;
		case ENTRANCE:
			level.entrance = this;
			walkable = true;
			break;
		case EXIT:
			level.exit = this;
			walkable = true;
			break;
		default:
			walkable = true;
			break;
		}
		
		}
		
	//wird aufgerufen, wenn dieses Feld betreten wird
	public void onEntry() {
		switch (type) {
		case ENTRANCE:
			level.game.changeLevel(level.game.currentLevelId-1, 1);
			break;
		case EXIT:
			level.game.changeLevel(level.game.currentLevelId+1, 0);
			break;
		default:
			break;
		}
		
	}
	
	
	
	//�ndert Koordinaten des Feldes
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
	
	
	
	
	

}
