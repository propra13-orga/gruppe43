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
	//Anzahl der Leben des Spielers
	int lives;
	//Status des Spiels: 0 = Spiel läuft, 1 = verloren, 2 = gewonnen
	int state = 0;
	//aktiver Checkpoint
	Field checkpoint;
	
	
	
	public Game(String[] lvlpath) {
		LEVEL_COUNT = lvlpath.length;
		player = new Player();
		lives = 2;
		
		//Levels laden
		for (int i = 0; i<LEVEL_COUNT;i++) {
			levels[i] = new Level(i);
			levels[i].init("lvl/"+lvlpath[i], this);
		}
		
		//Eingang des ersten und Ausgang des letzten Levels entfernen
		setCheckpoint(levels[0].entrance);
		levels[LEVEL_COUNT-1].exit.changeType(Field.OBJECTIVE);
		Enemy dude = new Enemy();
		dude.move(levels[0].exit, false);
		dude.facex=-1;
		player.move(levels[0].entrance, false);
		levels[0].getField(1, 3).addItem(Item.createItem(Item.ID_SWORD));
		levels[0].getField(13, 3).addItem(Item.createItem(Item.ID_TRASH_ARMOR));
		
		
	}
	
	
	//führt den nächsten Schritt des Spiels aus, ruft act() für alle Actors auf
	public void updateGame() {
		if (state == 0) {
			//alle Actors handeln lassen
			int i = 0;
			int a = getCurrentLevel().actors.size();
			while (i<a) {
				getCurrentLevel().actors.get(i).act();
				if (a == getCurrentLevel().actors.size()) i++;
				else a--;
			}
			//alle Effekte updaten
			i = 0;
			a = getCurrentLevel().effects.size();
			while (i<a) {
				getCurrentLevel().effects.get(i).update();
				if (a == getCurrentLevel().effects.size()) i++;
				else a--;
			}
			//Spieler am Checkpunkt wiederbeleben, falls tot
			if (getLives() > 0 && !player.isAlive()) {
				restoreToCheckpoint();
				changeLives(-1);
			}
		}
		
	}
	//wechselt zum Level mit der angegebenen ID
	//target gibt an zu welchem Feld sich der Spieler bewegt: 0 = Eingang, 1 = Ausgang,
	public void changeLevel(int id, int target) {
		currentLevelId = id;
		if (target==0) player.move(levels[currentLevelId].entrance, false);
		if (target==1) player.move(levels[currentLevelId].exit, false);
	}
	
	public void setCheckpoint(Field t) {
		if (checkpoint != null) checkpoint.changeType(Field.CHECKPOINT);
		t.changeType(Field.ACTIVE_CHECKPOINT);
		checkpoint = t;
	}
	
	public void restoreToCheckpoint() {
		player.restorePlayer();
		player.field = null;
		player.move(checkpoint);
		currentLevelId = checkpoint.getLevel().getId();
	}
	
	
	public Level getCurrentLevel() { return levels[currentLevelId]; }
	public int getLives() {return lives;}
	public void changeLives(int i) {lives+=i;}
	
	
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
