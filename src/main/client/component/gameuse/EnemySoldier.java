package main.client.component.gameuse;

import main.client.MainApplet;
import main.client.layout.GameStage;

public abstract class EnemySoldier extends Soldier{
	//constructor
	public EnemySoldier(GameStage gameStage, MainApplet parent, float x, float y){
		super(gameStage, parent, x, y);	
	}
	
	//display enemy soldier
	public void display(){
		super.getParent().stroke(255, 0, 0);
		super.getParent().ellipse(super.getX(), super.getY(), super.getSize(), super.getSize());
		super.getParent().stroke(0);
		super.getParent().fill(0);
		super.getParent().ellipse(super.getX()+super.getSize()/1.2f, 
				super.getY()-super.getSize()/3.5f, 2, 2);
		super.getParent().ellipse(super.getX()+super.getSize()/1.2f-7, 
				super.getY()-super.getSize()/3.5f, 2, 2);
	}
	
	//e one pixel
	public void move(){	super.setPosition(super.getX()+1, super.getY()); }
	
	//method need to implement
	public abstract void setupAttackAni();
}