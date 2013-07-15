package com.github.propra13.gruppe43.Dialogs;

import com.github.propra13.gruppe43.Game;
import com.github.propra13.gruppe43.Items.Inventory;
import com.github.propra13.gruppe43.Items.Item;

public class MainMenuDialog extends Dialog {
	public String[] options = {"Spiel beginnen", "Nutzlose Zeile", "Spiel verlassen"};
	public int selectionIndex;
	
	
	public MainMenuDialog(Game gm, String hl) {
		type = MAIN_MENU;
		headline = hl;
		game = gm;
		selectionIndex = 0;
	}
	
	
	public void selectionUp() {
		if (selectionIndex == 0) selectionIndex = options.length-1;
		else selectionIndex--;
		
	}

	public void selectionDown() {
		selectionIndex = (selectionIndex+1) % options.length;
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
			if (selectionIndex == 0) {
				game.launchGame();
				game.exitDialog();
			}
			if (selectionIndex == 2) System.exit(0);
			break;
		default:
			break;
		}
		
	}
	
}
