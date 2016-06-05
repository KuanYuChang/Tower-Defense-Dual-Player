package main.client.component;

import main.client.MainApplet;
import processing.core.PApplet;

public class MyArcherDisplay extends MySoldierDisplay {
	private float weaponPos, arrowPos;

	public MyArcherDisplay(MainApplet parent, float x, float y) {
		super(parent, x, y);
		this.weaponPos = -super.getSize()/1.5f;
		this.arrowPos = -super.getSize()/1.5f;
	}

	@Override
	public void display() {
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
	}
}
