package com.github.propra13.gruppe43.Items;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.DamageTypes;
import com.github.propra13.gruppe43.Field;

public class Weapon extends Item {
	//Menge an Schaden, die diese Waffe verursacht
	int damage;
	//Menge an Energy, die ein Angriff mit dieser Waffe verbraucht
	int energyCost;
	//speichert die Energiekosten, die der Actor vor anlegen der Waffe zum angreifen brauchte
	int energyCostOld = 0;
	
	
	public Weapon(int ID) {
		id = ID;
		type = TYPE_WEAPON;
		switch (id) {
			case ID_SWORD:
				damage = 25;
				energyCost = 300;
				break;
			default:
				damage = 10;
				energyCost = 300;
				break;
		}
	}
	
	public void onEquip(Actor a) {
		energyCostOld = a.costATTACK;
		a.costATTACK = energyCost;
	}
	
	public void onUnequip(Actor a) {
		a.costATTACK = energyCostOld;
		energyCostOld = 0;
	}
	
	
	//wird ausgelöst wenn Actor a  das Feld t mit dieser Waffe angreift, aus der Richtung des Vektors (facex, facey)
		public void onHit(Actor a, Field t, int facex, int facey) {
			if (a != null && t != null) {
				if (t.getActor()!= null) a.dealDamage(t.getActor(), damage, DamageTypes.PHYSICAL);
			}
		}
}
