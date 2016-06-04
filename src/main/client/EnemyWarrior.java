package main.client;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

public class EnemyWarrior extends EnemySoldier{
	private float weaponPos;
	
	//constructor
	public EnemyWarrior(GameStage gameStage, MainApplet parent, float x, float y){
		super(gameStage, parent, x, y);
		this.weaponPos = PApplet.PI/4*3;
		this.setupAttackAni();
	}
	
	//display enemy warrior
	public void display(){
		//set fill color
		super.getParent().fill(255, 0, 0, super.getGameStage().getEnemyWarriorLvl()*2);
		
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
			super.getAniSequence().add(Ani.to(this, 0.5f, "weaponPos", 0));
			super.getAniSequence().add(Ani.to(this, 0.5f, "weaponPos", PApplet.PI/4*3));
		super.getAniSequence().endSequence();
	}
}