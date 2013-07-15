package com.github.propra13.gruppe43.Items;
import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Field;

/**
 * Superklasse für Gegenstände.
 */
public class Item {
	//Typ des Items und ID des Items
	public int type = 0;
	int id = 0;
	//Item-Typen
	public final static int TYPE_WEAPON = 0;
	public final static int TYPE_ARMOR = 1;
	public final static int TYPE_POWERUP = 1000;
	//Item-IDs
	public final static int ID_SWORD = 0;
	public final static int ID_TRASH_ARMOR = 1;
	public final static int ID_HEALTH_POTION = 2;
	public final static int ID_MANA_POTION = 3;
	public final static int ID_GOLD = 4;
	public final static int ID_SPELLBOOK_FIREBALL = 5;
	public final static int ID_AXE = 6;
	public final static int ID_VICTORY_ARMOR = 7;
	// Name des Items
	public String name;
	//Goldwert des items
	public int cost = 0;


	public Item() {
	}
	
	public static Item createItem(int ID) {
		switch (ID) {
		case ID_SWORD:
		case ID_AXE:
			return new Weapon(ID);
		case ID_TRASH_ARMOR:
		case ID_VICTORY_ARMOR:
			return new Armor(ID);
		case ID_HEALTH_POTION:
		case ID_MANA_POTION:
		case ID_GOLD:
		case ID_SPELLBOOK_FIREBALL:
			return new Powerup(ID);
		default:
			return null;
		}
	}
	
	public void onPickup(Actor a) {
		
	}
	
	public void onEquip(Actor a) {
		
	}
	
	public void onUnequip(Actor a) {
		
	}
	
	
	//wird ausgelöst wenn Actor a  das Feld t mit dieser Waffe angreift, aus der Richtung des Vektors (facex, facey)
	public void onHit(Actor a, Field t, int facex, int facey) {
		
	}
	//wird ausgelöst wenn Actor a, der dieses Item trägt, durch Actor b Schaden vom Typ dType nimmt, gibt den durch Rüstung modifizierten Schadenswert zurück
	public double whenHit(Actor a, Actor attacker, double damage, int dType)  {
		return damage;
	}
	
	public String getName() {return name; }
	public int getCost() {return cost; }
	public int getId() { return id;}

}
