package com.github.propra13.gruppe43.Dialogs;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Game;
import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;
/**
 * Die Klasse InventoryDialog erlaubt dem Spieler, Items aus seinem Inventar auszurüsten.
 */
public class InventoryDialog extends Dialog {
	public Inventory inventory;
	public Actor actor;
	public Item selection;
	public int selectionIndex;
	
	/**
	 * Erzeugt einen neuen InventoryDialog.
	 * @param a Actor, dessen Inventar manipuliert wird.
	 */
	public InventoryDialog(Actor a) {
		type = INVENTORY;
		headline = "Inventar";
		inventory = a.getInventory();
		actor = a;
		game = a.getGame();
		selectionIndex = 0;
		if (inventory.items.size()==0) selection = null;
		else selection = inventory.items.get(0);
	}
	
	private void equip() {
		if (selection != null) {
				inventory.equipItem(selection);
				if (selectionIndex >= inventory.items.size()) selection = null;
				else selection = inventory.items.get(selectionIndex);
			}	
		
	} 
	
	private void drop() {
		if (selection != null) {
			inventory.drop(selection, actor.getField());
			if (selectionIndex >= inventory.items.size()) selection = null;
			else selection = inventory.items.get(selectionIndex);
		}	
		
	}
	
	
	private void selectionUp() {
		if (selectionIndex == 0) {
			selectionIndex = inventory.items.size();
			selection = null;
		}
		else {
			selectionIndex--;
			selection = inventory.items.get(selectionIndex);
		}
	}

	private void selectionDown() {
		selectionIndex++;
		if (selectionIndex < inventory.items.size()) selection = inventory.items.get(selectionIndex);
		else if (selectionIndex == inventory.items.size()) selection = null;
		else {
			selectionIndex = 0;
			if (inventory.items.size() == 0) selection = null;
			else selection = inventory.items.get(selectionIndex);
						
		}
	}
	
	
	//pressed und released definieren, wie auf Eingabe reagiert wird
	public void pressed(String key) {
		switch (key) {
		case "up":
			selectionUp();
			break;
		case "down":
			selectionDown();
			break;
		case "d":
			drop();
			break;
		case "space":
			if (selection == null) game.exitDialog();
			else equip();
			break;
		default:
			break;
		}
		
	}
	
}
