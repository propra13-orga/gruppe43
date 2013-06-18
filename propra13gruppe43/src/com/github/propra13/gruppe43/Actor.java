package com.github.propra13.gruppe43;
import java.awt.image.*;
import java.util.*;

import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;
public class Actor{
	// energy wird benötigt um zu handeln
	final int ENERGY_MAX = 1000;
	public int energy = ENERGY_MAX;
	public int energyGain = 6;
	
	//AI des Actors
	AI ai = null;
	
	//Lebenspunkte und Mana
	public int maxHealth = 100;
	public int health = 100;
	public int maxMana = 100;
	public int mana = 100;
	
	//Inventar des Actors
	Inventory inventory;
	
	//Zauber des Actors, und momentan ausgewählter Zauber
	public final static int SPELL_NUMBER = 4;
	Spell[] spells;
	int currentSpell;
	
	// Aktion, die ausgeführt wird
	public int currentAction = Actor.MOVE;
	public int nextAction = Actor.NONE;
	public int defaultAction = Actor.MOVE;
	//Aktionen
	public final static int MOVE = 0;
	public final static int ATTACK = 1;
	public final static int CAST_SPELL = 2;
	public final static int NONE = 1000;
	
	//benötigte Energie für Aktionen
	public int costMOVE = 60;
	public int costATTACK = 60;
	
	//Actor-Typen
	final static int PLAYER = 0;
	final static int ENEMY = 1;
	final static int TEXT_NPC = 2;
	final static int SHOP_NPC = 3;
	final static int BOSS = 4;

	//Typ dieses Actors
	public int type = 0;
	//Status des Actors: 1 = lebt, 0 = tot
	public int state = 1; 
	
	//Richtung, in die der Actor agiert:
	public int facex, facey;
	
	//Feld auf dem sich Actor befindet
	Field field = null;
	
	public Actor() {
		inventory = new Inventory(this);
		spells = new Spell[SPELL_NUMBER];
	}
	
	public void act() {
		if (this.state != 0) { //nur handeln, wenn der Player nicht tot ist
			
			//AI anwenden
			if (ai != null) ai.useAI(this);

			//Cooldown aller Zauber verringern
			for (int i=0;i<SPELL_NUMBER;i++) if(spells[i] !=null) spells[i].coolDown();
			
			//Energy erhöhen, handeln wenn genug Energy vorhanden ist
			if (energy < ENERGY_MAX) {
				changeEnergy(energyGain);
			}
			
			else {
				
				
				//verschiedene Aktionen ausführen
				if (nextAction == NONE) nextAction = currentAction; 

				Field targetField = this.getLevel().getField(this.field.x+facex, this.field.y+facey);
				switch (nextAction) {
					case Actor.MOVE:
						if (targetField != null && targetField != this.field) {
							if (this.move(targetField, true)) changeEnergy(-costMOVE);					
						}					
						break;
						
					case Actor.ATTACK:
						if (facex != 0 || facey != 0) if (attack()) changeEnergy(-costATTACK);
						break;
					
					case Actor.CAST_SPELL:
						if (spells[currentSpell] != null) {
							if (spells[currentSpell].castSpell(targetField, facex, facey)) changeEnergy(-spells[currentSpell].energyCost);
						}
						break;
						
					default:
						break;
					
				}
				nextAction = NONE;
						
			}
				
		}
			
	}
	
	
	//bewegt den Actor aus das Zielfeld, gibt true zurück, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgelöst wird
	public boolean move(Field t, boolean entry) {
		if (!t.isOccupied() ) {
			t.actor = this;
			if (field != null) {
				field.actor = null;
				if (field.getLevel() != t.getLevel()) {
					field.getLevel().removeActor(this);
					t.getLevel().addActor(this);
				}
			}
			else t.getLevel().addActor(this);
			this.field = t;
			if (entry) t.onEntry();
			return true;
					
		}
		return false;
	}
	
	//greift in die Richtung, in die der Actor schaut, an
	public boolean attack() {
		if (hasEquipped(Item.TYPE_WEAPON)) {
			inventory.getEquipment(Item.TYPE_WEAPON).onHit(this, getLevel().getField(field.x+facex, field.y+facey), facex, facey);
			return true;
		}
		else return false;
	}
	
	
	//wird ausgelöst, wennd er Spieler mit diesem Objekt interagiert
	public void interacted(Actor a) {
			
	}
	
	
	public void changeMana(int a) {
		mana = Math.min(maxMana, Math.max(0, mana+a));
	}
	
	public void changeEnergy(int a) {
		energy = Math.min(ENERGY_MAX, Math.max(0, energy+a));
	}
	
	public void changeHealth(int a) {
		health = Math.min(maxHealth, Math.max(0, health+a));
		if (health == 0) kill();
	}
	
	//
	
	//zerberstet den Actor
	public void kill() {
		state = 0;
		getLevel().removeActor(this);
		inventory.dropItems(field);
		field.actor = null;
		
	}

	//verursacht Schaden in Höhe von damage vom Typ dType am Actor a
	public void dealDamage(Actor a, int damage, int dType) {
		a.takeDamage(this, damage, dType);
		
	}
	
	//nimmt Schaden in Höhe von damage vom Typ dType durch den Actor a, eventuell modifiziert durch Rüstung
	public void takeDamage(Actor a, int damage, int dType) {
		
		if (this.hasEquipped(Item.TYPE_ARMOR))  damage = this.getInventory().getEquipment(Item.TYPE_ARMOR).whenHit(this, a, damage, dType);

		this.changeHealth(-Math.max(0, damage));
		
		
	}
	
	public boolean addSpell(Spell spell) {
		for (int i = 0; i<SPELL_NUMBER; i++) {
			if (spells[i] == null ) {
				spells[i] = spell;
				return true;
				
			}
			
		}
		return false;
	}
	
	public Inventory getInventory() { return this.inventory; }
	public boolean hasEquipped(int i) {return this.getInventory().hasEquipped(i); }
	
	public void setMaxHealth(int i) {
		maxHealth = i;
		changeHealth(i);
	}
	
	public void setMaxMana(int i) {
		maxMana = i;
		changeMana(i);
	}
	public boolean isAlive() { return (state != 0); }
	public int getHealth() { return this.health; }
	public int getMaxHealth() { return this.maxHealth; }
	public int getMana() {return this.mana; }
	public int getMaxMana() { return this.maxMana; }
	public Field getField() { return this.field; }
	public Level getLevel() { return this.field.level; }
	public Game getGame() {return this.field.level.game; }

	

	

}
