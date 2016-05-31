package main.client;

import processing.core.PApplet;

public abstract class MySoldierDisplay extends Component implements DetectMouse{
	private final float size = 30;
	
	public MySoldierDisplay(MainApplet parent, float x, float y) {
		super(parent, x, y);
	}
	
	//display my soldier
	public void display(){
		super.getParent().fill(255);
		super.getParent().stroke(0, 0, 255);
		super.getParent().ellipse(super.getX(), super.getY(), this.size, this.size);
		super.getParent().stroke(0);
		super.getParent().fill(0);
		super.getParent().ellipse(super.getX()-this.size/1.2f, 
				super.getY()-this.size/3.5f, 2, 2);
		super.getParent().ellipse(super.getX()-this.size/1.2f+7, 
				super.getY()-this.size/3.5f, 2, 2);
	}
	
	@Override
	public boolean mouseOn(){
		float disX = super.getParent().mouseX - super.getX();
		float disY = super.getParent().mouseY - super.getY();
		
		if(PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY)) < this.size){
			return true;
		}else{
			return false;
		}
	}
	
	//getter
	public float getSize(){ return this.size; }
}
