package com.github.propra13.gruppe43;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class TestFrame extends JFrame implements KeyListener{
	LevelPanel levelpanel;
	JPanel menupanel;
	JButton startbutton, quitbutton;
	JLabel label;
	Timer timer;
	public void init() {
		menupanel = new JPanel();	
		label = new JLabel("^_______^");
		label.setPreferredSize(new Dimension(200,30));
		
		startbutton = new JButton("Spiel starten");
		startbutton.setPreferredSize(new Dimension(200,50));
		startbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestFrame.this.remove(menupanel);
				launchGame();
						}
			
		});
		
		quitbutton = new JButton("Spiel verlassen");
		quitbutton.setPreferredSize(new Dimension(200,50));
		quitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
						}
			
		});
		menupanel.add(label);
		menupanel.add(startbutton);
		menupanel.add(quitbutton);
		menupanel.setPreferredSize(new Dimension(200,150));
		this.add(menupanel);
		this.pack();
		
		this.setTitle("Entspannt durchs Dungeon crawlen");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	public void launchGame() {
		levelpanel = new LevelPanel();
		levelpanel.init();
		//Timer, der alle 5ms das Spiel updatet
		timer = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				levelpanel.game.gameUpdate();
				//Fenstergröße verändern, wenn das Level gewechselt wird
				if (levelpanel.game.currentLevelId != levelpanel.displayLevel) {
					levelpanel.displayLevel = levelpanel.game.currentLevelId;
					levelpanel.setPreferredSize(new Dimension(levelpanel.game.levels[levelpanel.displayLevel].size_x*32,levelpanel.game.levels[levelpanel.displayLevel].size_y*32));
					pack();
					setLocationRelativeTo(null);
				}
				repaint();
				//Rückkehr in das Menü, wenn das Spiel vorbei ist
				if (levelpanel.game.state != 0) {
					timer.stop();
					timer = null;
					TestFrame.this.remove(levelpanel);
					for (int i = 0; i<=3;i++) keypress[i] = false;

					
					
					if (levelpanel.game.state == 1) label.setText("Trauriger Tod... :C");
					if (levelpanel.game.state == 2) label.setText("Glorreicher Sieg! C:");
					levelpanel = null;
					showMenu();
					
				}
			}});
		
		levelpanel.addKeyListener(this);
		this.add(levelpanel);
		levelpanel.setPreferredSize(new Dimension(levelpanel.game.levels[levelpanel.game.currentLevelId].size_x*32, levelpanel.game.levels[levelpanel.game.currentLevelId].size_y*32));
		pack();
		this.setLocationRelativeTo(null);
		timer.start();
		levelpanel.requestFocusInWindow();				
		
	}
	
	
	public void showMenu() {
		this.add(menupanel);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	

	
	
	//Steuerung durch Tastatur
	boolean[] keypress = new boolean[4];
	
	public void keyPressed(KeyEvent arg0) {
		int b = arg0.getKeyCode();
		if (!keypress[0] && b == KeyEvent.VK_W) { levelpanel.game.player.movey-=1; keypress[0] = true; }
		if (!keypress[1] && b == KeyEvent.VK_A) { levelpanel.game.player.movex-=1; keypress[1] = true; }
		if (!keypress[2] && b == KeyEvent.VK_S){ levelpanel.game.player.movey+=1; keypress[2] = true; }
		if (!keypress[3] && b == KeyEvent.VK_D) { levelpanel.game.player.movex+=1; keypress[3] = true; }
		
		
	}

	
	

	public void keyReleased(KeyEvent arg0) {
		int b = arg0.getKeyCode();
		if (keypress[0] && b == KeyEvent.VK_W) { levelpanel.game.player.movey+=1; keypress[0] = false; }
		if (keypress[1] && b == KeyEvent.VK_A) { levelpanel.game.player.movex+=1; keypress[1] = false; }
		if (keypress[2] && b == KeyEvent.VK_S){ levelpanel.game.player.movey-=1; keypress[2] = false; }
		if (keypress[3] && b == KeyEvent.VK_D) { levelpanel.game.player.movex-=1; keypress[3] = false; }
		
	}



	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public static void main(String[] args) {
		final TestFrame f = new TestFrame();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f.init();
			}
		});
		

	}	

}