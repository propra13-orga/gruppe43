package com.github.propra13.gruppe43;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;


public class Level {
	//Gr��e des Levels
	int size_x, size_y;
	//Felder des Levels
	ArrayList<ArrayList<Field>> fields;
	//Game zu dem dieses Level geh�rt
	Game game = null;
	//Ein- und Ausgang des Levels
	Field entrance = null;
	Field exit = null;
	
	
	public Level() {
		fields = new ArrayList<ArrayList<Field>>();
	}
	
	
	//liest ein Level aus einer Textdatei mit dem Pfad "path" ein und speichert dieses in dem Array fields
	public boolean init(String path, Game gm) {
		game = gm;
		String s;
		int c = 0;
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File(path));
			do {
				s = scanner.nextLine();
				for (int i = 0; i<s.length(); i++) {
					if (c==0) fields.add(new ArrayList<Field>());
					
					fields.get(i).add(new Field(s.charAt(i), this));
					fields.get(i).get(c).setxy(i, c);
				}
				c++;		

			} while (scanner.hasNextLine());
			size_x = fields.size();
			size_y = fields.get(0).size();
			return true;
			
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			return false;
		} finally {
			scanner.close();
		}
		
		

	}
	// gibt das Feld des Levels mit den Koordinaten x,y zur�ck
	public Field getField(int x, int y) {
		if (x<size_x && y<size_y) {
			return fields.get(x).get(y);
		}
		else {
			return null;
		}
	}


}
