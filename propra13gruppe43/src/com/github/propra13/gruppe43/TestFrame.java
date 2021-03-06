package com.github.propra13.gruppe43;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class TestFrame extends JFrame{
	JPanel panel;
	Timer timer;
	Game game;
	GameDisplay display;
	public void init() {
		String[] lvlpath = {"level1.txt", "level2.txt", "level3.txt", "level4.txt", "level5.txt", "level6.txt", "level7.txt", "level8.txt", "level9.txt"};
		game = new Game(lvlpath);
		display = new GameDisplay(game);
		display.init();
		panel = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(display.getDisplay(), 0, 0, null);
			}
		};

		timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.updateGame();
				display.updateDisplay();
				repaint();
			}});
		
		panel.setPreferredSize(new Dimension(GameDisplay.WINDOW_SIZE_X,GameDisplay.WINDOW_SIZE_Y));
		panel.addKeyListener(game.keys);
		this.add(panel);
		
		timer.start();
		
		this.pack();	
		this.setTitle("Entspannt durchs Dungeon crawlen");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);		
		panel.requestFocusInWindow();
		
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