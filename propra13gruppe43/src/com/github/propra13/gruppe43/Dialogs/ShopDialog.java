package com.github.propra13.gruppe43.Dialogs;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Game;
import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;
/**
 * Die Klasse ShopDialog, bietet Spielern die Möglichkeit, aus dem Inventar eines Händlers
 * Items zu kaufen.
 */
public class ShopDialog extends Dialog {
	public Inventory inventory;
	public Actor buyer;
	public Item selection;
	public int selectionIndex;
	
	/**
	 * Erzeugt einen neuen ShopDialog.
	 * @param gm Spiel, zu dem der Dialog gehört.
	 * @param inv Inventar, aus dem Items gekauft werden können.
	 * @param b Actor, der den Käufer darstellt.
	 * @param hl Kopfzeile des Dialogs.
	 */
	public ShopDialog(Game gm, Inventory inv, Actor b, String hl) {
		type = SHOP;
		headline = hl;
		inventory = inv;
		buyer = b;
		game = gm;
		selectionIndex = 0;
		if (inventory.items.size()==0) selection = null;
		else selection = inventory.items.get(0);
	}
	
	private void buy() {
		if (selection != null) {
			if (buyer.getInventory().getGold() >= selection.getCost()) {
				buyer.getInventory().changeGold(-selection.getCost());
				inventory.items.remove(selection);
				buyer.getInventory().pickup(selection);
				if (selectionIndex >= inventory.items.size()) selection = null;
				else selection = inventory.items.get(selectionIndex);
			}
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
		case "space":
			if (selection == null) game.exitDialog();
			else buy();
			break;
		default:
			break;
		}
		
	}
	
}
