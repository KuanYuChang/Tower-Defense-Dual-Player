package main.client.component;

import main.client.MainApplet;

public abstract class Component {
	private MainApplet parent;
	private float x, y;
	
	//constructor
	public Component(MainApplet parent, float x, float y){
		this.parent = parent;
		this.setPosition(x,  y);
	}
	
	//setter
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	//getter
	public MainApplet getParent(){ return this.parent; }
	public float getX(){ return this.x; }
	public float getY(){ return this.y; }
	
	//method needs to implement
	public abstract void display();
}