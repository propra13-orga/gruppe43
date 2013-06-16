package com.github.propra13.gruppe43;
import java.awt.image.*;
import java.util.*;

import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;
public class Actor{
	// energy wird benötigt um zu handeln
	final int ENERGY_MAX = 1000;
	public int energy = ENERGY_MAX;
	public int energyGain = 40;
	
	//Lebenspunkte und Mana
	public int maxhealth = 100;
	public int health = 100;
	public int mana = 100;
	
	//Inventar des Actors
	Inventory inventory;
	
	// Aktion, die ausgeführt wird
	public int currentAction = Actor.MOVE;
	public int defaultAction = Actor.MOVE;
	//Aktionen
	public final static int MOVE = 0;
	public final static int ATTACK = 1;
	
	//benötigte Energie für Aktionen
	public int costMOVE = 300;
	public int costATTACK = 300;
	
	//Actor-Typen
	final static int PLAYER = 0;

	//Typ dieses Actors
	public int type = 0;
	//Status des Actors: 0 = lebt, 1 = tot
	public int state = 0; 
	
	//Richtung, in die der Actor agiert:
	public int facex, facey;
	
	//Feld auf dem sich Actor befindet
	Field field = null;
	
	public Actor() {
		inventory = new Inventory(this);
	}
	
	public void act() {
		
	}
	
	
	//bewegt den Actor aus das Zielfeld, gibt true zurück, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgelöst wird
	public boolean move(Field t, boolean entry) {
		if (!t.isOccupied() ) {
			t.actor = this;
			if (field != null) {
				field.actor = null;
				if (field != t) field.getLevel().removeActor(this);
			}
			if (field != t) t.getLevel().addActor(this);
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
	
	private void changeHealth(int a) {
		health = Math.min(maxhealth, Math.max(0, health+a));
		if (health == 0) kill();
	}
	
	
	//
	
	//zerberstet den Actor
	public void kill() {
		state = 1;
		getLevel().actors.remove(this);
		field.actor = null;
		inventory.dropItems(getField());
		
	}

	//verursacht Schaden in Höhe von damage vom Typ dType am Actor a
	public void dealDamage(Actor a, int damage, int dType) {
		a.takeDamage(this, damage, dType);
		
	}
	
	//nimmt Schaden in Höhe von damage vom Typ dType durch den Actor a, eventuell modifiziert durch Rüstung
	public void takeDamage(Actor a, int damage, int dType) {
		if (damage<0) damage=0;
		
		if (this.hasEquipped(Item.TYPE_ARMOR))  damage = this.getInventory().getEquipment(Item.TYPE_ARMOR).whenHit(this, a, damage, dType);
		
		this.changeHealth(-damage);
		
		
	}
	
	
	
	public Inventory getInventory() { return this.inventory; }
	public boolean hasEquipped(int i) {return this.getInventory().hasEquipped(i); }
	
	public int getHealth() { return this.health; }
	public int getMana() {return this.mana; }
	public Field getField() { return this.field; }
	public Level getLevel() { return this.field.level; }
	public Game getGame() {return this.field.level.game; }

	

	

}
