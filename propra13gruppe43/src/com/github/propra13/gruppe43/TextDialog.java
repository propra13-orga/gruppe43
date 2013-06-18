package com.github.propra13.gruppe43;

public class TextDialog extends Dialog {
	String[] text;
	
	
	public TextDialog(Game gm, String hl, String[] txt) {
		type = TEXT;
		headline = hl;
		text = txt;
		game = gm;
	}

}
