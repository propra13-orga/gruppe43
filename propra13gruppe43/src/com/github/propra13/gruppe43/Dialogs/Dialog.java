package com.github.propra13.gruppe43.Dialogs;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.Game;
import com.github.propra13.gruppe43.KeyInterface;
import com.github.propra13.gruppe43.Items.Inventory;

public class Dialog implements KeyInterface{
	//Spiel zu dem der Dialog gehört
	public Game game;
	//Typ des Dialogs
	public int type;
	//Dialog-Typen
	public final static int MAIN_MENU = 0;
	public final static int TEXT = 1;
	public final static int SHOP = 2;
	public final static int INVENTORY = 3;
	//Überschrift des Dialogs
	public String headline;
	
	static public Dialog createTextDialog(Game gm, String hl, String[] txt) {
		return new TextDialog(gm, hl, txt);
	}
	
	static public Dialog createShopDialog(Game gm, Inventory inv, Actor b, String hl) {
		return new ShopDialog(gm, inv, b, hl);
	}
	
	static public Dialog createInventoryDialog(Actor a) {
		return new InventoryDialog(a);
	}
	
	static public Dialog createMainMenuDialog(Game gm, String hl) {
		return new MainMenuDialog(gm, hl);
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
