package com.github.propra13.gruppe43;

import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;

public class ShopDialog extends Dialog {
	Inventory inventory;
	Actor buyer;
	Item selection;
	int selectionIndex;
	
	
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
	
	public void buy() {
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
	
	
	public void selectionUp() {
		if (selectionIndex == 0) {
			selectionIndex = inventory.items.size();
			selection = null;
		}
		else {
			selectionIndex--;
			selection = inventory.items.get(selectionIndex);
		}
	}

	public void selectionDown() {
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
