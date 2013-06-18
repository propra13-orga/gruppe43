package com.github.propra13.gruppe43.Items;

import Effects.Effect;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.DamageTypes;
import com.github.propra13.gruppe43.Field;
import com.github.propra13.gruppe43.Projectile;

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
				name = "Schwert";
				cost = 100;
				damage = 25;
				energyCost = 150;
				break;
			case ID_AXE:
				name = "Axt";
				cost = 200;
				damage = 50;
				energyCost = 250;
				break;
			default:
				damage = 10;
				energyCost = 150;
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
					if (t.isOccupied()) a.dealDamage(t.getActor(), damage, DamageTypes.PHYSICAL);
					switch (id) {
					case ID_SWORD:
					case ID_AXE:
						new Effect(t, Effect.SLASH);
						break;
					default:
					break;
				}
			}
		}
}
