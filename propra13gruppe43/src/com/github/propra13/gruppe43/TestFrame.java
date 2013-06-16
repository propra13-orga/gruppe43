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
		String[] lvlpath = {"level1.txt", "level2.txt", "level3.txt"};
		game = new Game(lvlpath);
		display = new GameDisplay(game);
		display.init();
		panel = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(display.getDisplay(), 0, 0, null);
			}
		};

		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.updateGame();
				display.updateDisplay();
				repaint();
			}});
		
		panel.setPreferredSize(new Dimension(GameDisplay.WINDOW_SIZE_X-10,GameDisplay.WINDOW_SIZE_Y-10));
		panel.addKeyListener(new KeyInput(game));
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