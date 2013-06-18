package com.github.propra13.gruppe43;

public class Boss extends Enemy {

	public Boss( AI a, int h, int m, int speed, int drop) {
		super(BOSS, a, h, m, speed, drop);
	}

	public void kill() {
		super.kill();
		this.getLevel().entrance.changeType(Field.ENTRANCE);
		if (getGame().currentLevelId<8) this.getLevel().exit.changeType(Field.EXIT);
		else  this.getLevel().exit.changeType(Field.OBJECTIVE);
	}

}
