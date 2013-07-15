package Effects;

import com.github.propra13.gruppe43.Actor;
import com.github.propra13.gruppe43.DamageTypes;
import com.github.propra13.gruppe43.Field;

public class Effect {
	//Dauer des Effektes
	int duration;
	//Besitzer des Effektes
	Actor owner;
	//Schaden des Effektes
	double damage;
	int dType;
	//Typ des Effektes
	public int type;
	//Effekt-Typen
	public final static int SLASH = 0;
	public final static int FIRE_SPLASH = 1;
	//Momentaner Zustand des Effektes
	public int state;
	//Feld, das dieser Effekt betrifft
	public Field target;
	
	
	public Effect(Actor a, Field t, int tp, double d, int dmgType) {
		type = tp;
		target = t;
		target.getLevel().addEffect(this);
		owner = a;
		damage = d;
		dType = dmgType;
		state = 0;
		switch (type) {
		case SLASH:
			duration = 10;
			break;
		case FIRE_SPLASH:
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
				if (duration == 5)	if (target.isOccupied()) owner.dealDamage(target.getActor(), damage, DamageTypes.PHYSICAL);
				break;
			default:
				break;
			
			}
		}
		else kill();
	}
	
	public void kill() {
		duration = 0;
		state = 0;
		target.getLevel().removeEffect(this);
	}
}
