package com.github.propra13.gruppe43;

public class TextNPC extends Actor {
	String[] text;
	String headline;
	
	public TextNPC (String hl, String[] txt) {
		type = TEXT_NPC;
		headline = hl;
		text = txt;
		
	}
	
	public void interacted(Actor a) {
		getGame().enterDialog(Dialog.createTextDialog(getGame(), headline, text));
		
	}

}
