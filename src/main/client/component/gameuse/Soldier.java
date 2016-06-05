package main.client.component.gameuse;

import de.looksgood.ani.AniSequence;
import main.client.MainApplet;
import main.client.layout.GameStage;

public abstract class Soldier extends GameComponent{
	private final float size = 20;
	private AniSequence seq;
	
	//constructor
	public Soldier(GameStage gameStage, MainApplet parent, float x, float y) {
		super(gameStage, parent, x, y);
		this.seq = new AniSequence(super.getParent());
	}
	
	//getter
	public float getSize(){ return this.size; }
	public AniSequence getAniSequence(){ return this.seq; }
	
	//animation for attack
	public void attack(){
		if(!this.seq.isPlaying())
			this.seq.start();
		else if(this.seq.isEnded())
			this.seq.start();
	}
	
	//methods need to implement
	public abstract void display();
	public abstract void setupAttackAni();
	public abstract void move();
}