package com.github.propra13.gruppe43;

public class Enemy extends Actor {
	
	
	//Typ, AI des Actors, Max. Leben, Max. Mana, energyGain, drop gibt an wie Items fallen gelassen werden
	public Enemy(int tp, AI a, int h, int m, double speed, int drop) {
		super();
		type = tp;
		ai = a;
		setMaxHealth(h);
		setMaxMana(m);
		energyGain = speed;
		inventory.setDrop(drop);
	}
	
	
	
	//bewegt den Actor aus das Zielfeld, gibt true zurück, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgelöst wird
	public boolean move(Field t, boolean entry) {
		if (t.isWalkable()) {
			return super.move(t, entry);
					
		}
		return false;
	}
}
