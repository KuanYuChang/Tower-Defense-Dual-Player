package main.client.component.gameuse;

import de.looksgood.ani.Ani;
import main.client.MainApplet;
import main.client.layout.GameStage;
import processing.core.PApplet;

public class MyWarrior extends MySoldier{
	private float weaponPos;
	
	//constructor
	public MyWarrior(GameStage gameStage, MainApplet parent, float x, float y){
		super(gameStage, parent, x, y);
		this.weaponPos = PApplet.PI/4;
		this.setupAttackAni();
	}
	
	//display my warrior
	public void display(){
		//set fill color
		super.getParent().fill(0, 0, 255, super.getGameStage().getMyWarriorLvl()*2);
		
		//display soldier
		super.display();
		
		//display weapon
		super.getParent().stroke(0);
		super.getParent().line(super.getX(), super.getY(), 
				super.getX()+super.getSize()*PApplet.cos(this.weaponPos)*1.5f, 
				super.getY()-super.getSize()*PApplet.sin(this.weaponPos)*1.5f);
		super.getParent().line(super.getX(), super.getY(), 
				super.getX()+super.getSize()*PApplet.cos(this.weaponPos-PApplet.PI/2)/3, 
				super.getY()-super.getSize()*PApplet.sin(this.weaponPos-PApplet.PI/2)/3);
		super.getParent().line(super.getX(), super.getY(), 
				super.getX()+super.getSize()*PApplet.cos(this.weaponPos+PApplet.PI/2)/3, 
				super.getY()-super.getSize()*PApplet.sin(this.weaponPos+PApplet.PI/2)/3);
		super.getParent().line(super.getX(), super.getY(), 
				super.getX()+super.getSize()*PApplet.cos(this.weaponPos+PApplet.PI)/3, 
				super.getY()-super.getSize()*PApplet.sin(this.weaponPos+PApplet.PI)/3);
	}
	
	//setup animation for attack
	@Override
	public void setupAttackAni() {
		super.getAniSequence().beginSequence();
			super.getAniSequence().add(Ani.to(this, 0.5f, "weaponPos", PApplet.PI));
			super.getAniSequence().add(Ani.to(this, 0.5f, "weaponPos", PApplet.PI/4));
		super.getAniSequence().endSequence();
	}
}