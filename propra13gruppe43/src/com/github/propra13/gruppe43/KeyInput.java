package com.github.propra13.gruppe43;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{
	//Ziel, das durch die Tasten angesteuert wird
	KeyInterface target;
	//das Array keypress enthält, welche Tasten gedrückt sind
	boolean[] keypress = new boolean[13];
	String[] inputStrings = {"up","left","down","right","q","w","e","r","a","s", "space", "i", "d"};
	
	
	public KeyInput(KeyInterface k) {
		target = k;
	}
	
	
	public void keyPressed(KeyEvent arg0) {
		int b = arg0.getKeyCode();
		if (!keypress[0] && b == KeyEvent.VK_UP) { target.pressed("up"); keypress[0] = true; }
		if (!keypress[1] && b == KeyEvent.VK_LEFT) { target.pressed("left"); keypress[1] = true; }
		if (!keypress[2] && b == KeyEvent.VK_DOWN){ target.pressed("down"); keypress[2] = true; }
		if (!keypress[3] && b == KeyEvent.VK_RIGHT) { target.pressed("right"); keypress[3] = true; }
		if (!keypress[4] && b == KeyEvent.VK_Q) { target.pressed("q"); keypress[4] = true; }
		if (!keypress[5] && b == KeyEvent.VK_W) { target.pressed("w"); keypress[5] = true; }
		if (!keypress[6] && b == KeyEvent.VK_E) { target.pressed("e"); keypress[6] = true; }
		if (!keypress[7] && b == KeyEvent.VK_R) { target.pressed("r"); keypress[7] = true; }
		

		if (!keypress[8] && b == KeyEvent.VK_A) { target.pressed("a"); keypress[8] = true; }

		if (!keypress[10] && b == KeyEvent.VK_SPACE) { target.pressed("space"); keypress[10] = true; }
		if (!keypress[11] && b == KeyEvent.VK_I) { target.pressed("i"); keypress[11] = true; }
		if (!keypress[12] && b == KeyEvent.VK_D) { target.pressed("d"); keypress[12] = true; }
		
	}

	
	

	public void keyReleased(KeyEvent arg0) {
		int b = arg0.getKeyCode();
		if (keypress[0] && b == KeyEvent.VK_UP) { target.released("up"); keypress[0] = false; }
		if (keypress[1] && b == KeyEvent.VK_LEFT) { target.released("left"); keypress[1] = false; }
		if (keypress[2] && b == KeyEvent.VK_DOWN){ target.released("down"); keypress[2] = false; }
		if (keypress[3] && b == KeyEvent.VK_RIGHT) { target.released("right"); keypress[3] = false; }
		if (keypress[4] && b == KeyEvent.VK_Q) { target.released("q"); keypress[4] = false; }
		if (keypress[5] && b == KeyEvent.VK_W) { target.released("w"); keypress[5] = false; }
		if (keypress[6] && b == KeyEvent.VK_E) { target.released("e"); keypress[6] = false; }
		if (keypress[7] && b == KeyEvent.VK_R) { target.released("r"); keypress[7] = false; }


		if (keypress[8] && b == KeyEvent.VK_A) { target.released("a"); keypress[8] = false; }
		
		if (keypress[10] && b == KeyEvent.VK_SPACE) { target.released("space"); keypress[10] = false; }
		if (keypress[11] && b == KeyEvent.VK_I) { target.released("i"); keypress[11] = false; }
		if (keypress[12] && b == KeyEvent.VK_D) { target.released("d"); keypress[12] = false; }
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
