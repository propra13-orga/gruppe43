package com.github.propra13.gruppe43.Items;

import java.util.ArrayList;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Field;
/**
 * Klasse für das Inventar eines Actors. Sie verwaltet die Liste aller Items des Actors und 
 * kann Items aufnehmen, ablegen und ausrüsten.  
 */

public class Inventory {
	//Actor, der dieses Inventar besitzt
	Actor actor;
	//Items, die getragen werden
	public final static int EQSLOTS = 2;
	Item[] equipment;
	
	//Items im Inventar
	public ArrayList<Item> items;
	
	//Gold
	public int gold = 0;
	
	//legt fest, welche Items bei Tod fallengelassen werden: 0 = keine Items, 1 = nicht ausgerüstete Items, 2 = alle Items
	int drop = 0;
	
	//legt fest, ob Items automatisch aufgehoben werden
	boolean pickup = false;
	
	
	public Inventory(Actor a) {
		actor = a;
		items = new ArrayList<Item>();
		equipment = new Item[EQSLOTS];
		
	}
	
	
	public void equipItem(Item item) {
		unequipItem(item.type);
		items.remove(item);
		equipment[item.type] = item;
		item.onEquip(actor);
		
	}
	
	public void unequipItem(int type) {	
		if(equipment[type] != null) {
			items.add(equipment[type]); 
			equipment[type].onUnequip(actor);
			equipment[type] = null;
		}	
		
	}
	
	
	public void unequipAll() {
		for (int i = 0; i<EQSLOTS; i++) unequipItem(i);
	}
	
	//nimmt das Item item auf, p gibt an ob onPickup ausgelöst wird und das Item ausgerüstet wird
	public void pickup(Item i, boolean p) {
		items.add(i);
		if (p) {
		if ((i.type < EQSLOTS) && equipment[i.type] == null)  equipItem(i);
		i.onPickup(actor);
		}
	}
	
	public void pickup(Item i) { pickup(i, true); }
	/**
	 * Nimmt alle Items, die sich auf einem Feld befinden, ins Inventar auf.
	 * @param t Zielfeld.
	 */
	public void pickupItems(Field t) {
		if (pickup) {
			ArrayList<Item> i = t.getItems();
			while (i.size() != 0) {
				pickup(i.get(0));
				i.remove(0);
			}
		}
	}
	
	public void drop(Item i, Field t) {
		t.addItem(i);
		items.remove(i);
	}
	
	/**
	 * Sofern erlaubt, bewegt diese Methode alle Items aus diesem Inventar auf das Zielfeld.
	 * @param t Zielfeld.
	 */
	public void dropItems(Field t) {
		if (drop == 2) unequipAll();
		if (drop != 0) {
			if (gold != 0) {
				Powerup g = new Powerup(Item.ID_GOLD);
				g.value = gold;
				gold = 0;
				t.addItem(g);
			}
			while (items.size() != 0) {
				drop(items.get(0), t);
			}
		 }
	}
	public Item getEquipment(int i) {return equipment[i];}
	public boolean hasEquipped(int i) {return (equipment[i] != null);}
	public void setDrop (int b) {drop = b;}
	public void setPickup (boolean b) {pickup = b;}
	public void changeGold(int i) {gold = Math.max(0, gold+i);}
	public int getGold() {return gold;}
}
