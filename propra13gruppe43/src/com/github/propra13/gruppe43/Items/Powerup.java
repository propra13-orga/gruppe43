package com.github.propra13.gruppe43.Items;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Spell;
/**
 * Klasse für Powerup-Gegenstände. Powerups werden beim Aufsammeln ausgelöst.
 */
public class Powerup extends Item {
	
	int value;
	
	public Powerup(int ID) {
		id = ID;
		type = TYPE_POWERUP;
		switch (id) {
			case ID_HEALTH_POTION:
				name = "Heiltrank";
				cost = 50;
				value = 75;
				break;
			case ID_MANA_POTION:
				name ="Manatrank";
				cost = 50;
				value = 75;
			case ID_GOLD:
				break;
			case ID_SPELLBOOK_FIREBALL:
				name = "Zauberbuch: Feuerball";
				cost = 300;
				break;
			default:
				break;
		}
	}
	
	public void onPickup(Actor a) {
		a.getInventory().items.remove(this);
		switch (id) {
		case ID_HEALTH_POTION:
			a.changeHealth(value);
			break;
		case ID_MANA_POTION:
			a.changeMana(value);
			break;
		case ID_GOLD:
			a.getInventory().changeGold(value);
			break;
		case ID_SPELLBOOK_FIREBALL:
			a.addSpell(new Spell(a, Spell.FIREBALL));
			break;
		default:
			break;
		
		}
		
	}
}
