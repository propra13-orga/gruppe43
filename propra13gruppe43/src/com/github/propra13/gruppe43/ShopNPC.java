package com.github.propra13.gruppe43;

import com.github.propra13.gruppe43.Dialogs.Dialog;

public class ShopNPC extends Actor {
	String headline;
	public ShopNPC (String hl) {
		type = SHOP_NPC;
		headline = hl;
		
	}
	
	public void interacted(Actor a) {
		getGame().enterDialog(Dialog.createShopDialog(getGame(), this.inventory, a, headline));
		
	}

}
