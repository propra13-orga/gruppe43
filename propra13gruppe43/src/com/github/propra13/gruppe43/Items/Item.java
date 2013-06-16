package com.github.propra13.gruppe43.Items;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Field;

public class Item {
	//Typ des Items und ID des Items
	int type = 0;
	int id = 0;
	//Item-Typen
	public final static int TYPE_WEAPON = 0;
	public final static int TYPE_ARMOR = 1;
	//Item-IDs
	public final static int ID_SWORD = 0;
	public final static int ID_TRASH_ARMOR = 1;


	public Item() {
	}
	
	public static Item createItem(int ID) {
		switch (ID) {
		case ID_SWORD:
			return new Weapon(ID);
		case ID_TRASH_ARMOR:
			return new Armor(ID);
		default:
			return null;
		}
	}
	public void onEquip(Actor a) {
		
	}
	
	public void onUnequip(Actor a) {
		
	}
	
	
	//wird ausgelöst wenn Actor a  das Feld t mit dieser Waffe angreift, aus der Richtung des Vektors (facex, facey)
	public void onHit(Actor a, Field t, int facex, int facey) {
		
	}
	//wird ausgelöst wenn Actor a, der dieses Item trägt, durch Actor b Schaden vom Typ dType nimmt, gibt den durch Rüstung modifizierten Schadenswert zurück
	public int whenHit(Actor a, Actor attacker, int damage, int dType)  {
		return damage;
	}

}
