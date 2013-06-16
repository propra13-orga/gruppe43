package com.github.propra13.gruppe43;

public class Enemy extends Actor {
	//bewegt den Actor aus das Zielfeld, gibt true zur�ck, falls erfolgreich, sonst false
	//entry gibt an, ob die Methode onEntry des Zielfeldes ausgel�st wird
	public boolean move(Field t, boolean entry) {
		if (t.isWalkable()) {
			return super.move(t, entry);
					
		}
		return false;
	}
}
