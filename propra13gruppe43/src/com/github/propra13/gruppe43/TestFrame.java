package com.github.propra13.gruppe43;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class TestFrame extends JFrame{
	
	public void init() {
		final Timer timer;
		final LevelPanel panel = new LevelPanel();
		panel.init();
		ActionListener herbert = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.game.gameUpdate();
				repaint();
				
			}
			
		};
		timer = new Timer(5, herbert);
		this.addKeyListener(new KeyListener() {
			boolean[] keypress = new boolean[4];
			@Override
			public void keyPressed(KeyEvent arg0) {
				int b = arg0.getKeyCode();
				if (!keypress[0] && b == KeyEvent.VK_W) { panel.game.player.movey-=1; keypress[0] = true; }
				if (!keypress[1] && b == KeyEvent.VK_A) { panel.game.player.movex-=1; keypress[1] = true; }
				if (!keypress[2] && b == KeyEvent.VK_S){ panel.game.player.movey+=1; keypress[2] = true; }
				if (!keypress[3] && b == KeyEvent.VK_D) { panel.game.player.movex+=1; keypress[3] = true; }
				
				
			
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				int b = arg0.getKeyCode();
				if (keypress[0] && b == KeyEvent.VK_W) { panel.game.player.movey+=1; keypress[0] = false; }
				if (keypress[1] && b == KeyEvent.VK_A) { panel.game.player.movex+=1; keypress[1] = false; }
				if (keypress[2] && b == KeyEvent.VK_S){ panel.game.player.movey-=1; keypress[2] = false; }
				if (keypress[3] && b == KeyEvent.VK_D) { panel.game.player.movex-=1; keypress[3] = false; }
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(panel);
		panel.setPreferredSize(new Dimension(panel.game.levels[panel.game.currentLevelId].size_x*32,panel.game.levels[panel.game.currentLevelId].size_y*32));
		this.pack();
		timer.start();		
		this.setVisible(true);
		
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