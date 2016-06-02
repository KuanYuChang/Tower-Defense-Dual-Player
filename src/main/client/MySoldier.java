package main.client;

public abstract class MySoldier extends Soldier{
	//constructor
	public MySoldier(GameStage gameStage, MainApplet parent, float x, float y){
		super(gameStage, parent, x, y);
	}
	
	//display my soldier
	@Override
	public void display(){
		super.getParent().stroke(0, 0, 255);
		super.getParent().ellipse(super.getX(), super.getY(), super.getSize(), super.getSize());
		super.getParent().stroke(0);
		super.getParent().fill(0);
		super.getParent().ellipse(super.getX()-super.getSize()/1.2f, 
				super.getY()-super.getSize()/3.5f, 2, 2);
		super.getParent().ellipse(super.getX()-super.getSize()/1.2f+7, 
				super.getY()-super.getSize()/3.5f, 2, 2);
	}
	
	//move one pixel
	public void move(){	super.setPosition(super.getX()-1, super.getY()); }
	
	//method need to implement
	public abstract void setupAttackAni();
}