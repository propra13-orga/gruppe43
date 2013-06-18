package com.github.propra13.gruppe43;
public class PatrolAI extends AI {
	int patrolx;
	int patroly;

	public PatrolAI(Actor t, int px, int py) {
		super(t);
		patrolx = px;
		patroly = py;		
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
			if (!actor.getLevel().getField(actor.getField().x+patrolx, actor.getField().y+patroly).isWalkable()) {
				patrolx = -patrolx;
				patroly = -patroly;
			}
			actor.facex = patrolx;
			actor.facey = patroly;
		}
	}

	
	 

}
	
	 


