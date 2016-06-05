package main.client.component;

import main.client.MainApplet;
import processing.core.PApplet;

public class MyWarriorDisplay extends MySoldierDisplay {
	private float weaponPos;
	
	public MyWarriorDisplay(MainApplet parent, float x, float y) {
		super(parent, x, y);
		this.weaponPos = PApplet.PI/4;
	}

	@Override
	public void display() {				
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
}
