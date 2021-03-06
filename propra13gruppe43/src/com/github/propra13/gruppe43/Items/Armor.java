package com.github.propra13.gruppe43.Items;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.DamageTypes;
/**
 * Klasse f�r Waffen-Gegenst�nde.
 */
public class Armor extends Item {
	double damageFactor;
	double damageReduction;
	
	public Armor(int ID) {
		id = ID;
		type = TYPE_ARMOR;
		switch (id) {
			case ID_TRASH_ARMOR:
				name = "Wertlose R�stung";
				cost = 100;
				damageFactor = 0.8;
				damageReduction = 0;
				break;
			case ID_VICTORY_ARMOR:
				name = "R�stung des Sieges";
				cost = 9001;
				damageFactor = 0.1;
				damageReduction = 0;
				break;
				
			default:
				damageFactor = 0.8;
				damageReduction = 0;
				break;
		}
	}
	
	//wird ausgel�st wenn Actor a, der dieses Item tr�gt, durch Actor b Schaden vom Typ dType nimmt
		public double whenHit(Actor a, Actor attacker, double damage, int dType)  {
			if (dType == DamageTypes.PHYSICAL) damage = (damageFactor * (damage-damageReduction));
			return damage;
		}
}
