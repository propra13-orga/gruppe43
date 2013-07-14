package com.github.propra13.gruppe43;
import java.awt.image.*; 
import java.util.*;
import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;


/**
 * Die Klasse Actor stellt ein im Spiel handelndes Objekt dar, beispielsweise den Spieler, Gegner und alle NPCs.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class Actor{
	// energy wird ben�tigt um zu handeln
	final double ENERGY_MAX = 1000;
	public double energy = ENERGY_MAX;
	public double energyGain = 12;
	
	//AI des Actors
	AI ai = null;
	
	//Lebenspunkte und Mana
	public double maxHealth = 100;
	public double health = 100;
	public double healthRegen = 0;
	public double maxMana = 100;
	public double mana = 100;
	public double manaRegen = 0;
	
	//Inventar des Actors
	Inventory inventory;
	
	//Zauber des Actors, und momentan ausgew�hlter Zauber
	public final static int SPELL_NUMBER = 4;
	Spell[] spells;
	int currentSpell;
	
	// Aktion, die ausgef�hrt wird
	public int currentAction = Actor.MOVE;
	public int nextAction = Actor.NONE;
	public int defaultAction = Actor.MOVE;
	//Aktionen
	public final static int MOVE = 0;
	public final static int ATTACK = 1;
	public final static int CAST_SPELL = 2;
	public final static int NONE = 1000;
	
	//ben�tigte Energie f�r Aktionen
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
	
	//Feld auf dem sich Actor befindet, und das vorherige Feld
	Field field = null;
	//ben�tigt f�r Anzeige
	Field fieldLast = null;
	double posSpeed = 0;
	double maxPos = 10;
	double currentPos = 0;
	
	
	public Actor() {
		inventory = new Inventory(this);
		spells = new Spell[SPELL_NUMBER];
	}
	
	public void act() {
		if (this.state != 0) { //nur handeln, wenn der Player nicht tot ist
			
			//Leben und Mana regenerieren
			changeHealth(healthRegen);
			changeMana(manaRegen);
			
			//AI anwenden
			if (ai != null) ai.useAI(this);

			//Cooldown aller Zauber verringern
			for (int i=0;i<SPELL_NUMBER;i++) if(spells[i] !=null) spells[i].coolDown();
			
			//Energy erh�hen, handeln wenn genug Energy vorhanden ist
			if (energy < ENERGY_MAX) {
				changeEnergy(energyGain);
				if (fieldLast != field) currentPos=Math.min(currentPos+posSpeed, maxPos);
			}
			
			else {
				fieldLast = field;
				
				//verschiedene Aktionen ausf�hren
				if (nextAction == NONE) nextAction = currentAction; 

				Field targetField = this.getLevel().getField(this.field.x+facex, this.field.y+facey);
				switch (nextAction) {
					case Actor.MOVE:
						if (targetField != null && targetField != this.field) {
							if (this.move(targetField, true))  {
								changeEnergy(-costMOVE);
								currentPos = 0;
								maxPos = costMOVE;
								posSpeed = energyGain;
							}
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
	
	
	//bewegt den Actor aus das Zielfeld, gibt true zur�ck, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgel�st wird
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
			if(fieldLast == null || fieldLast.getLevel() != field.getLevel()) fieldLast = field;
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
	
	
	//wird ausgel�st, wennd er Spieler mit diesem Objekt interagiert
	public void interacted(Actor a) {
			
	}
	
	
	public void changeMana(double a) {
		mana = Math.min(maxMana, Math.max(0, mana+a));
	}
	
	public void changeEnergy(double a) {
		energy = Math.min(ENERGY_MAX, energy+a);
	}
	
	public void changeHealth(double a) {
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

	//verursacht Schaden in H�he von damage vom Typ dType am Actor a
	public void dealDamage(Actor a, int damage, int dType) {
		a.takeDamage(this, damage, dType);
		
	}
	
	//nimmt Schaden in H�he von damage vom Typ dType durch den Actor a, eventuell modifiziert durch R�stung
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
	public double getHealth() { return this.health; }
	public double getMaxHealth() { return this.maxHealth; }
	public double getMana() {return this.mana; }
	public double getMaxMana() { return this.maxMana; }
	public Field getField() { return this.field; }
	public Level getLevel() { return this.field.level; }
	public Game getGame() {return this.field.level.game; }
	

	public Field getFieldLast() { return this.fieldLast; }
	
	public double calcOffsetx() {
		return  ((field.x - fieldLast.x) * ((maxPos-currentPos) / ((double) maxPos))); 
	}
	
	public double calcOffsety() {
		return ((field.y - fieldLast.y) * ((maxPos-currentPos) / ((double) maxPos))); 
	}

	

	

}
