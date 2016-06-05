package main.client.component.gameuse;

import de.looksgood.ani.Ani;
import main.client.MainApplet;
import main.client.layout.GameStage;
import processing.core.PApplet;

public class MyArcher extends MySoldier{
	private float weaponPos, arrowPos;
	
	//constructor
	public MyArcher(GameStage gameStage, MainApplet parent, float x, float y){
		super(gameStage, parent, x, y);
		this.weaponPos = -super.getSize()/1.5f;
		this.arrowPos = -super.getSize()/1.5f;
		this.setupAttackAni();
	}
	
	//display my archer
	public void display(){
		//set fill color
		super.getParent().fill(0, 0, 255, super.getGameStage().getMyArcherLvl()*2);
		
		//display soldier
		super.display();
		
		//display weapon
		super.getParent().noFill();
		super.getParent().stroke(0);
		super.getParent().arc(super.getX()-super.getSize()/1.5f, super.getY(), 
				super.getSize()/1.5f, super.getSize(), 
				PApplet.PI/2, PApplet.PI/2*3);
		super.getParent().line(super.getX()+this.weaponPos, super.getY(), 
				super.getX()-super.getSize()/1.5f, super.getY()-super.getSize());
		super.getParent().line(super.getX()+this.weaponPos, super.getY(), 
				super.getX()-super.getSize()/1.5f, super.getY()+super.getSize());
		super.getParent().stroke(0, 0, 255);
		super.getParent().line(super.getX()+this.arrowPos, super.getY(), 
				super.getX()+this.arrowPos-super.getSize()*2, super.getY());
		
		if(super.getAniSequence().isEnded()){
			this.arrowPos = -super.getSize()/1.5f;
		}
	}
	
	//setup animation for attack
	@Override
	public void setupAttackAni() {
		super.getAniSequence().beginSequence();
			super.getAniSequence().beginStep();
				super.getAniSequence().add(Ani.to(this, 0.5f, "weaponPos", super.getSize()/2));
				super.getAniSequence().add(Ani.to(this, 0.5f, "arrowPos", super.getSize()/2));
			super.getAniSequence().endStep();
			super.getAniSequence().beginStep();
				super.getAniSequence().add(Ani.to(this, 0.5f, "weaponPos", -super.getSize()/1.5f));
				super.getAniSequence().add(Ani.to(this, 0.5f, "arrowPos", -super.getSize()*2));
			super.getAniSequence().endStep();
		super.getAniSequence().endSequence();
	}
}