package com.github.propra13.gruppe43.Dialogs;

import com.github.propra13.gruppe43.Game;

public class TextDialog extends Dialog {
	public String[] text;
	
	
	public TextDialog(Game gm, String hl, String[] txt) {
		type = TEXT;
		headline = hl;
		text = txt;
		game = gm;
	}

}
