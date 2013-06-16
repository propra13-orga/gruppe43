package com.github.propra13.gruppe43.Items;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.DamageTypes;

public class Armor extends Item {
	double damageFactor;
	int damageReduction;
	
	public Armor(int ID) {
		id = ID;
		type = TYPE_ARMOR;
		switch (id) {
			case ID_TRASH_ARMOR:
				damageFactor = 0.8;
				damageReduction = 0;
				break;
			default:
				damageFactor = 0.8;
				damageReduction = 0;
				break;
		}
	}
	
	//wird ausgelöst wenn Actor a, der dieses Item trägt, durch Actor b Schaden vom Typ dType nimmt
		public int whenHit(Actor a, Actor attacker, int damage, int dType)  {
			if (dType == DamageTypes.PHYSICAL) damage = (int) (damageFactor * (damage-damageReduction));
			return damage;
		}
}
