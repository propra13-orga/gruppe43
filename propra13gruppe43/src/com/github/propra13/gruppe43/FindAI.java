package com.github.propra13.gruppe43;


public class FindAI extends AI {

	public FindAI(Actor t) {
		super(t);	
	}
	
	public void useAI(Actor actor) {
		int difx = target.getField().x - actor.getField().x;
		int dify = target.getField().y - actor.getField().y;
		
		if ((difx>=-1 && difx<=1) && (dify>=-1 && dify<=1)) {
			actor.currentAction = Actor.ATTACK;
			actor.facex = difx;
			actor.facey = dify;
		}
		else {
			actor.currentAction = Actor.MOVE;
			if (difx<0) actor.facex = -1;
			else if (difx>0) actor.facex = 1;
			else actor.facex = 0;
			
			if (dify<0) actor.facey = -1;
			else if (dify>0) actor.facey = 1;
			else actor.facey = 0;
		}
	}
	
}

