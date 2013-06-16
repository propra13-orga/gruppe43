package com.github.propra13.gruppe43.Items;

import java.util.ArrayList;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Field;


public class Inventory {
	Actor actor;
	//Items, die getragen werden
	final static int EQSLOTS = 2;
	Item[] equipment;
	
	//Items im Inventar
	ArrayList<Item> items;
	
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
	//bewegt alle Items auf dem Feld t ins Inventar
	public void pickupItems(Field t) {
		if (pickup) {
			ArrayList<Item> i = t.getItems();
			while (i.size() != 0) {
				items.add(i.get(0));
				if (i.get(0).type < EQSLOTS) if (equipment[i.get(0).type] == null)  equipItem(i.get(0));
				i.remove(0);
			}
		}
	}
	
	
	//lässt die Items im Inventar auf das Feld t fallen
	public void dropItems(Field t) {
		if (drop == 2) unequipAll();
		if (drop != 0) {
			while (items.size() != 0) {
				t.addItem(items.get(0));
				items.remove(0);
			}
		 }
	}
	public Item getEquipment(int i) {return equipment[i];}
	public boolean hasEquipped(int i) {return (equipment[i] != null);}
	public void setDrop (int b) {drop = b;}
	public void setPickup (boolean b) {pickup = b;}
}
