package main.client.component.gameuse;

import main.client.MainApplet;
import main.client.component.Button;
import main.client.component.DetectMouse;
import main.client.layout.GameStage;

public abstract class SoldierButton extends GameComponent implements DetectMouse {
	private float width, height;
	private Button upgrade;
	
	//constructor
	public SoldierButton(GameStage gameStage, MainApplet parent, float x, float y, float width, float height) {
		super(gameStage, parent, x, y);
		this.width = width;
		this.height = height;
		this.upgrade = new Button(super.getParent(), "upgrade", super.getX(), super.getY()+this.height/3, this.width*0.9f, this.height/4);
	}
	
	//display button
	@Override
	public void display() {
		if(this.mouseOn() && !super.getParent().mousePressed){
			super.getParent().stroke(0, 0, 255);
		}else if(this.mouseOn() && super.getParent().mousePressed){
			super.getParent().stroke(255, 0, 0);
		}else{
			super.getParent().stroke(0);
		}
		super.getParent().strokeWeight(2);
		super.getParent().noFill();
		super.getParent().rect((super.getX()-this.width/2), (super.getY()-this.height/2), this.width, this.height);
		this.upgrade.display();
	}
	
	//detect where mouse is
	@Override
	public boolean mouseOn() {
		if(super.getParent().mouseX >= super.getX()-this.width/2
		&& super.getParent().mouseX <= super.getX()+this.width/2
		&& super.getParent().mouseY >= super.getY()-this.height/2
		&& super.getParent().mouseY <= super.getY()+this.height/2
		&& !this.upgrade.mouseOn()){
			return true;
		}else{
			return false;
		}	
	}
	
	//detect mouse on upgrade button
	public boolean mouseOnUpgrade(){
		return this.upgrade.mouseOn();
	}
	
	//getter
	public float getWidth()		{ return this.width; }
	public float getHeight()	{ return this.height; }
}