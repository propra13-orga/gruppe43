package com.github.propra13.gruppe43;

public class Spell {
	//Besitzer des Zaubers
	Actor owner;
	//Cooldown des Zaubers
	private int cooldown = 0;
	private int currentCooldown = 0;
	//Mana- und Energiekosten des Zaubers
	int manaCost = 0;
	int energyCost = 0;
	//Typ des Zaubers
	int type = 0;
	//Zauber-Typen
	public final static int FIREBALL = 0;
	
	public Spell(Actor a, int tp) {
		owner = a;
		type = tp;
		switch (type) {
		case FIREBALL:
			cooldown = 0;
			manaCost = 25;
			energyCost = 120;
			break;
		default:
			break;
			
		}
	}
	
	
	public boolean castSpell(Field t, int facex, int facey) {
		if (currentCooldown == 0 && owner.getMana() >= manaCost) {
			switch (type) {
			case FIREBALL:
				if (t == null || (facex == 0 && facey == 0)) return false;
				new Projectile(Projectile.FIREBALL, owner, t, facex, facey, 20, 7, 40, DamageTypes.FIRE);				
				break;
			default:
				break;
				
			}
			
			currentCooldown = cooldown;
			owner.changeMana(-manaCost);		
			return true;
		}
		else return false;
	}

	
	public void coolDown() {
		if (currentCooldown > 0) currentCooldown--; 
	}
}
