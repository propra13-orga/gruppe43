package com.github.propra13.gruppe43;

import java.awt.image.BufferedImage;

import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;

public class Player extends Actor implements KeyInterface{
	
	public Player() {
		super();
		type = PLAYER;
		healthRegen = 0.01;
		manaRegen = 0.05;
		this.getInventory().setDrop(0);
		this.getInventory().setPickup(true);
		
	}
	//erhöht energy, wenn ENERGY_MAX erreicht ist, wird energy verwendet um eine Aktion durchzuführen.
	public void act() {
		super.act();
	}
	
	public void restorePlayer() {
		health = maxHealth;
		mana = maxMana;
		state = 1;
		energy = 500;
	}
	
	//bewegt den Actor aus das Zielfeld, gibt true zurück, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgelöst wird
	public boolean move(Field t, boolean entry) {
		//interagieren, falls sich ein Actor auf dem Zielfeld befindet
		if (t.isOccupied()) t.getActor().interacted(this);
		
		if (t.isWalkable()) {
			return super.move(t, entry);
					
		}
		return false;
	}
	
	
	
	public boolean move(Field t) {
		return this.move(t, true);
	}
	
	

	


//pressed und released definieren, wie auf Eingabe reagiert wird
public void pressed(String key) {
	switch (key) {
	case "up":
		facey-=1;
		break;
	case "left":
		facex-=1;
		break;
	case "down":
		facey+=1;
		break;
	case "right":
		facex+=1;
		break;
	case "q":
		if (spells[0] != null) {
			currentSpell = 0;
			currentAction = Actor.CAST_SPELL;
			nextAction = Actor.CAST_SPELL;
		}
		break;
	case "w":
		if (spells[1] != null) {
			currentSpell = 1;
			currentAction = Actor.CAST_SPELL;
			nextAction = Actor.CAST_SPELL;
		}
		break;
	case "e":
		if (spells[2] != null) {
			currentSpell = 2;
			currentAction = Actor.CAST_SPELL;
			nextAction = Actor.CAST_SPELL;
		}
		break;
	case "r":
		if (spells[3] != null) {
			currentSpell = 3;
			currentAction = Actor.CAST_SPELL;
			nextAction = Actor.CAST_SPELL;
		}
		break;
		
	case "a":
		currentAction = Actor.ATTACK;
		nextAction = Actor.ATTACK;
		break;
	case "space":
		break;
	default:
		break;
	}
	
}

public void released(String key) {

	switch (key) {
	case "up":
		facey+=1;
		break;
	case "left":
		facex+=1;
		break;
	case "down":
		facey-=1;
		break;
	case "right":
		facex-=1;
		break;
	case "q":
		if (currentAction == Actor.CAST_SPELL && currentSpell == 0) currentAction = defaultAction;
		break;
	case "w":
		if (currentAction == Actor.CAST_SPELL && currentSpell == 1) currentAction = defaultAction;
		break;
	case "e":
		if (currentAction == Actor.CAST_SPELL && currentSpell == 2) currentAction = defaultAction;
		break;
	case "r":
		if (currentAction == Actor.CAST_SPELL && currentSpell == 3) currentAction = defaultAction;
		break;
	case "a":
		if (currentAction == Actor.ATTACK) currentAction = defaultAction;
		break;
	case "space":
		break;
	default:
		break;
	}
	
}

}


