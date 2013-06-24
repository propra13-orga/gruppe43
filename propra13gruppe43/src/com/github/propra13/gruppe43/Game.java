package com.github.propra13.gruppe43;

import java.awt.event.KeyEvent;

import com.github.propra13.gruppe43.Items.Armor;
import com.github.propra13.gruppe43.Items.Item;
import com.github.propra13.gruppe43.Items.Weapon;

public class Game implements KeyInterface{
	//Anzahl der Levels, Levels des Spiels, und der Index des momentan aktiven Levels
	int LEVEL_COUNT = 0;
	String[] lvlpath;
	int currentLevelId = 0;
	Level[] levels;
	//Ziel der Steuerung und KeyInput
	KeyInput keys;
	KeyInterface keyTarget;
	//gibt den momentanen Dialog an, und ob dieser aktiv ist
	Dialog dialog;
	boolean dialogActive = false;
	//Figur des Spielers
	Player player;
	//Anzahl der Leben des Spielers
	int lives;
	//Status des Spiels: 0 = Spiel läuft, 1 = verloren, 2 = gewonnen, 3 = pausiert
	int state = PAUSED;
	public final static int RUNNING = 0;
	public final static int LOST = 1;
	public final static int WON = 2;
	public final static int PAUSED = 3;
	//aktiver Checkpoint
	Field checkpoint;
	
	
	
	public Game(String[] lvlp) {
		lvlpath = lvlp;
		keys = new KeyInput(this);
		enterDialog(Dialog.createMainMenuDialog(this, "^_______^"));
		
	}
	
	public void launchGame() {
		currentLevelId = 0;
		LEVEL_COUNT = lvlpath.length;
		player = new Player();
		lives = 3;
		keyTarget = player;
		
		levels = new Level[10];
		
		//Levels laden
		for (int i = 0; i<LEVEL_COUNT;i++) {
			levels[i] = new Level(i);
			levels[i].init("lvl/"+lvlpath[i], this);
		}
		

		
		//Eingang des ersten und Ausgang des letzten Levels entfernen
		setCheckpoint(levels[0].entrance);
		levels[LEVEL_COUNT-1].exit.changeType(Field.OBJECTIVE);
		player.move(levels[0].entrance, false);
		prepareLevels();
		state = RUNNING;
		
	}
	
	public void prepareLevels() {
		//Level 1
		String[] text = {"Es ist gefährlich zu gehen alleine.", "Nimm dies.", " ", "(Benutze 'A' und die Pfeiltasten um anzugreifen.)", "(Benutze SPACE um diesen Dialog zu beenden.)"};
		TextNPC guy = new TextNPC("Hans",text);
		guy.move(levels[0].getField(5, 1), true);
		levels[0].getField(6, 1).addItem(Item.createItem(Item.ID_SWORD));
		//Level 2
		Enemy boy = new Enemy(Actor.ENEMY, new PatrolAI(player, 1, 0), 50, 50, 3, 1);
		boy.move(levels[1].getField(10, 3), false);
		boy.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
		boy = new Enemy(Actor.ENEMY, new PatrolAI(player, 1, 0), 50, 50, 3, 1);
		boy.move(levels[1].getField(7, 5), false);
		boy.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
		boy = new Enemy(Actor.ENEMY, new PatrolAI(player, 1, 0), 50, 50, 3, 1);
		boy.move(levels[1].getField(8, 7), false);
		boy.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
		boy = new Enemy(Actor.ENEMY, new PatrolAI(player, 1, 0), 50, 50, 3, 1);
		boy.move(levels[1].getField(5, 9), false);
		boy.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
		boy.getInventory().items.add(Item.createItem(Item.ID_HEALTH_POTION));
		//Level 3
		levels[2].entrance.changeType(Field.FLOOR);
		levels[2].exit.changeType(Field.FLOOR);
		Boss dude = new Boss( new FindAI(player), 150, 0, 3, 1);
		dude.getInventory().changeGold(200);
		dude.move(levels[2].getField(15, 2), false);
		dude.getInventory().equipItem(Item.createItem(Item.ID_SWORD));
		//Level 4
		ShopNPC man = new ShopNPC("Verkäufer"); 
		man.getInventory().items.add(Item.createItem(Item.ID_AXE));
		man.getInventory().items.add(Item.createItem(Item.ID_TRASH_ARMOR));
		man.move(levels[3].getField(1, 1), true);
		//Level 5
		levels[4].getField(1, 1).addItem(Item.createItem(Item.ID_SPELLBOOK_FIREBALL));
		boy = new Enemy(Actor.ENEMY, new PatrolAI(player, 0, 1), 75, 50, 5, 1);
		boy.move(levels[1].getField(8, 7), false);
		boy.getInventory().equipItem(Item.createItem(Item.ID_AXE));
		//Level 6
		levels[5].getField(2, 2).addItem(Item.createItem(Item.ID_MANA_POTION));
		levels[5].entrance.changeType(Field.FLOOR);
		levels[5].exit.changeType(Field.FLOOR);
		dude = new Boss( new FindAI(player), 200, 0, 5, 1);
		dude.getInventory().changeGold(500);
		dude.move(levels[5].getField(15, 2), false);
		dude.getInventory().equipItem(Item.createItem(Item.ID_AXE));	
		//Level 7
		//Level 8
		man = new ShopNPC("Verkäufer"); 
		man.getInventory().items.add(Item.createItem(Item.ID_VICTORY_ARMOR));
		man.getInventory().setDrop(1);
		man.move(levels[7].getField(1, 1), true);
		//Level 9
		levels[8].entrance.changeType(Field.FLOOR);
		levels[8].exit.changeType(Field.FLOOR);
		dude = new Boss( new FindAI(player), 300, 0, 8, 1);
		dude.getInventory().changeGold(9000);
		dude.move(levels[8].getField(15, 2), false);
		dude.getInventory().equipItem(Item.createItem(Item.ID_AXE));
		
	}
	
	
	//führt den nächsten Schritt des Spiels aus, ruft act() für alle Actors auf
	public void updateGame() {
		if (state == RUNNING && !dialogActive) {
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
			
			//alle Projektile updaten
			i = 0;
			a = getCurrentLevel().projectiles.size();
			while (i<a) {
				getCurrentLevel().projectiles.get(i).update();
				if (a == getCurrentLevel().projectiles.size()) i++;
				else a--;
			}
			
			//Spieler am Checkpunkt wiederbeleben, falls tot
			if (getLives() > 0 && !player.isAlive()) {
				restoreToCheckpoint();
				changeLives(-1);
			}
			
			//Spiel verloren, wenn Spieler tot und leblos
			if (getLives() == 0 && !player.isAlive()) {
				state = LOST;
			}
			
			if (state == WON) enterDialog(Dialog.createMainMenuDialog(this, "Gewonnen! :---------D"));
		
			if (state == LOST) enterDialog(Dialog.createMainMenuDialog(this, "Verloren... -,      -"));
			
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
		player.fieldLast = null;
		player.move(checkpoint);
		currentLevelId = checkpoint.getLevel().getId();
	}
	
	
	public void enterDialog(Dialog d) {
		dialog = d;
		keys.resetKeys();
		keyTarget = dialog;
		dialogActive = true;
	}
	
	public void exitDialog() {
		dialog  = null;
		keys.resetKeys();
		keyTarget = player;
		dialogActive = false;
	}
	
	
	public Level getCurrentLevel() { return levels[currentLevelId]; }
	public int getLives() {return lives;}
	public void changeLives(int i) {lives+=i;}
	
	
	//pressed und released definieren, wie auf Eingabe reagiert wird
	public void pressed(String key) {
		keyTarget.pressed(key);
		
	}
	
	public void released(String key) {
		keyTarget.released(key);
		
	}

	}
