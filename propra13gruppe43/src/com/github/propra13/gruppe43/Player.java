package com.github.propra13.gruppe43;

import java.awt.image.BufferedImage;

public class Player extends Actor{


	
	public Player() {
		super();
		type = PLAYER;
		this.getInventory().setDrop(2);
		this.getInventory().setPickup(true);
		
	}
	//erhöht energy, wenn ENERGY_MAX erreicht ist, wird energy verwendet um eine Aktion durchzuführen.
	public void act() {
		if (this.state != 1) { //nur handeln, wenn der Player nicht tot ist
			if (energy < ENERGY_MAX) {
				energy+=energyGain;
			}
			else {
				
				  //nur Aktionen ausführen, wenn der Actor in eine bestimmte Richtung schaut
					
					energy = ENERGY_MAX;
					//verschiedene Aktionen ausführen
					switch (currentAction) {
						case Actor.MOVE:
							Field targetField;
							if (( targetField = this.getLevel().getField(this.field.x+facex, this.field.y+facey)) != null) {
								if (this.move(targetField)) energy-=costMOVE;					
							}					
							break;
						case Actor.ATTACK:
							if (facex != 0 || facey != 0) if (attack()) energy-=costATTACK;
							break;
						default:
							break;
						
					}
				
				}
		}
		
	}
	
	//bewegt den Actor aus das Zielfeld, gibt true zurück, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgelöst wird
	public boolean move(Field t, boolean entry) {
		if (t.isWalkable()) {
			return super.move(t, entry);
					
		}
		return false;
	}
	
	public boolean move(Field t) {
		return this.move(t, true);
	}

}
