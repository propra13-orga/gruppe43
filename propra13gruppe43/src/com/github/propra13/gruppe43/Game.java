package com.github.propra13.gruppe43;

import java.awt.event.KeyEvent;

import com.github.propra13.gruppe43.Items.Armor;
import com.github.propra13.gruppe43.Items.Item;
import com.github.propra13.gruppe43.Items.Weapon;

public class Game implements KeyInterface{
	//Anzahl der Levels, Levels des Spiels, und der Index des momentan aktiven Levels
	int LEVEL_COUNT = 0;
	int currentLevelId = 0;
	Level[] levels = new Level[10];
	//Figur des Spielers
	Player player;
	//Status des Spiels: 0 = Spiel läuft, 1 = verloren, 2 = gewonnen
	int state = 0;
	
	
	
	public Game(String[] lvlpath) {
		LEVEL_COUNT = lvlpath.length;
		player = new Player();
		
		//Levels laden
		for (int i = 0; i<LEVEL_COUNT;i++) {
			levels[i] = new Level();
			levels[i].init("lvl/"+lvlpath[i], this);
		}
		
		//Eingang des ersten und Ausgang des letzten Levels entfernen
		levels[0].entrance.changeType(Field.FLOOR);
		levels[LEVEL_COUNT-1].exit.changeType(Field.OBJECTIVE);
		Player dude = new Player();
		dude.move(levels[0].exit, false);
		dude.type = 1;
		dude.facex=-1;
		player.move(levels[0].entrance, false);
		levels[0].getField(1, 3).addItem(Item.createItem(Item.ID_SWORD));
		levels[0].getField(13, 3).addItem(Item.createItem(Item.ID_TRASH_ARMOR));
		
		
	}
	
	
	//führt den nächsten Schritt des Spiels aus, ruft act() für alle Actors auf
	public void updateGame() {
		int a = getCurrentLevel().actors.size();
		if (state == 0) {
			int i = 0;
			while (i<a) {
				getCurrentLevel().actors.get(i).act();
				if (a == getCurrentLevel().actors.size()) i++;
				else a--;
			}
		}
		
	}
	//wechselt zum Level mit der angegebenen ID
	//target gibt an zu welchem Feld sich der Spieler bewegt: 0 = Eingang, 1 = Ausgang
	public void changeLevel(int id, int target) {
		currentLevelId = id;
		if (target==0) player.move(levels[currentLevelId].entrance, false);
		if (target==1) player.move(levels[currentLevelId].exit, false);
	}
	
	public Level getCurrentLevel() { return levels[currentLevelId]; }
	
	
	
	//pressed und released definieren, wie auf Eingabe reagiert wird
	public void pressed(String key) {
		switch (key) {
		case "up":
			player.facey-=1;
			break;
		case "left":
			player.facex-=1;
			break;
		case "down":
			player.facey+=1;
			break;
		case "right":
			player.facex+=1;
			break;
		case "a":
			player.currentAction = Actor.ATTACK;
			break;
		default:
			break;
		}
		
	}
	
	public void released(String key) {

		switch (key) {
		case "up":
			player.facey+=1;
			break;
		case "left":
			player.facex+=1;
			break;
		case "down":
			player.facey-=1;
			break;
		case "right":
			player.facex-=1;
			break;
		case "a":
			player.currentAction = player.defaultAction;
			break;
		default:
			break;
		}
		
	}

	}
