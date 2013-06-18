package com.github.propra13.gruppe43;

import com.github.propra13.gruppe43.Items.Inventory;

public class Dialog implements KeyInterface{
	//Spiel zu dem der Dialog gehört
	Game game;
	//Typ des Dialogs
	int type;
	//Dialog-Typen
	public final static int TEXT = 0;
	public final static int SHOP = 1;
	//Überschrift des Dialogs
	public String headline;
	
	static public Dialog createTextDialog(Game gm, String hl, String[] txt) {
		return new TextDialog(gm, hl, txt);
	}
	
	static public Dialog createShopDialog(Game gm, Inventory inv, Actor b, String hl) {
		return new ShopDialog(gm, inv, b, hl);
	}
	
	
	
	
	public void pressed(String key) {

		switch (key) {
		case "space":
			game.exitDialog();
			break;
		default:
			break;
		}
		
	}
	@Override
	public void released(String key) {
		// TODO Auto-generated method stub
		
	}

	

}
