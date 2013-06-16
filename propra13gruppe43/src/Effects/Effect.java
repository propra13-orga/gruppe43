package Effects;

import com.github.propra13.gruppe43.Field;

public class Effect {
	//Dauer des Effektes
	int duration;
	//Typ des Effektes
	public int type;
	//Effekt-Typen
	public final static int SLASH = 0;
	//Momentaner Zustand des Effektes
	public int state;
	//Feld, das dieser Effekt betrifft
	public Field target;
	
	
	public Effect(Field t, int tp) {
		type = tp;
		target = t;
		target.getLevel().addEffect(this);
		state = 0;
		switch (type) {
		case SLASH:
			duration = 10;
			break;
		default:
			break;
		
		}
	}
	
	
	public void update() {
		if (duration > 0) {
			duration--;
			switch (type) {
			case SLASH:
				state = (int) (4 - duration/(2.5));
				break;
			default:
				break;
			
			}
		}
		else kill();
	}
	
	public void kill() {
		System.out.println("dead");
		duration = 0;
		state = 0;
		target.getLevel().removeEffect(this);
	}
}
