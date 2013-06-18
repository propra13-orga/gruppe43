package com.github.propra13.gruppe43;

import Effects.Effect;

public class Projectile {

	// Geschwindigkeit des Projektils
	public int speedc = 0;
	public int speed = 0;
	
	//Anzahl an Feldern, die sich das Projektil maximal bewegen kann
	int reach = 0;
	
	//Schaden und Schadenstyp des Projektils
	int damage = 0;
	int dType = 0;
	
	//Typ des Projektils
	int type = 0;
	//Projektil-Typen
	public final static int FIREBALL = 0;

	//Richtung, in die sich das Projektil bewegt
	public int facex, facey;
	
	//Feld auf dem sich das Projectile befindet
	Field field = null;
	
	//Actor, dem das Projektil gehört
	Actor owner = null;
	
	//
	public Projectile(int tp, Actor a, Field t, int fx, int fy, int spd, int rch, int dmg, int dp) {
		type = tp;
		owner = a;
		field = t;
		facex = fx;
		facey = fy;
		speed = spd;
		reach = rch;
		damage = dmg;
		dType = dp;
		field.getLevel().addProjectile(this);
		//Projektil sofort zerschmettern, falls Feld ungültig
		if (this.move(field)) reach--;
		else reach = 0;
		
	}
	
	public void update() {
		if (field.isOccupied() && field.getActor() != owner) reach = 0;
		
		if (reach != 0) { //nur handeln, wenn der Player nicht tot ist
			if (speedc < 100) {
				speedc+=speed;
			}
			else {				
				speedc = 0;
				Field targetField;
				if (( targetField = field.getLevel().getField(this.field.x+facex, this.field.y+facey)) != null) {
					if (this.move(targetField)) reach--;
					else reach = 0;
				}
				else reach = 0;
				
				}
			if (field.isOccupied() && field.getActor() != owner) reach = 0;
		}
		if (reach == 0) kill();
		
	}
	
	//bewegt das Projektil aus das Zielfeld, gibt true zurück, falls erfolgreich, sonst false
	public boolean move(Field t) {
		if (t.isWalkable() ) {
			this.field = t;
			return true;
					
		}
		return false;
	}
	
	
	//zerplatzt das Projektil
		public void kill() {
			reach = 0;
			field.getLevel().removeProjectile(this);
			if (field.isOccupied() && field.getActor() != owner) owner.dealDamage(field.getActor(), damage, dType);
			switch (type) {
			case FIREBALL:
				new Effect(field, Effect.FIRE_SPLASH);
				break;
			default:
				break;
			}
			
		}

}
