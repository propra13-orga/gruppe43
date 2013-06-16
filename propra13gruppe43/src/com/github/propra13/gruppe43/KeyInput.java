package com.github.propra13.gruppe43;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{
	//Ziel, das durch die Tasten angesteuert wird
	KeyInterface target;
	//das Array keypress enthält, welche Tasten gedrückt sind
	boolean[] keypress = new boolean[10];
	String[] inputStrings = {"up","left","down","right","q","w","e","r","a","s"};
	
	
	public KeyInput(KeyInterface k) {
		target = k;
	}
	
	
	public void keyPressed(KeyEvent arg0) {
		int b = arg0.getKeyCode();
		if (!keypress[0] && b == KeyEvent.VK_UP) { target.pressed("up"); keypress[0] = true; }
		if (!keypress[1] && b == KeyEvent.VK_LEFT) { target.pressed("left"); keypress[1] = true; }
		if (!keypress[2] && b == KeyEvent.VK_DOWN){ target.pressed("down"); keypress[2] = true; }
		if (!keypress[3] && b == KeyEvent.VK_RIGHT) { target.pressed("right"); keypress[3] = true; }
		

		if (!keypress[8] && b == KeyEvent.VK_A) { target.pressed("a"); keypress[8] = true; }
		
		
	}

	
	

	public void keyReleased(KeyEvent arg0) {
		int b = arg0.getKeyCode();
		if (keypress[0] && b == KeyEvent.VK_UP) { target.released("up"); keypress[0] = false; }
		if (keypress[1] && b == KeyEvent.VK_LEFT) { target.released("left"); keypress[1] = false; }
		if (keypress[2] && b == KeyEvent.VK_DOWN){ target.released("down"); keypress[2] = false; }
		if (keypress[3] && b == KeyEvent.VK_RIGHT) { target.released("right"); keypress[3] = false; }

		if (keypress[8] && b == KeyEvent.VK_A) { target.released("a"); keypress[8] = false; }
	}
	
	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//setzt alle gedrückten Tasten zurück
	public void resetKeys() {
		for (int i=0;i<10;i++) {
			if (keypress[i]) { target.released(inputStrings[i]); keypress[i] = false; }
		}
		
	}
}
