package main.client;

import processing.core.PApplet;

public class GameButtonLabel extends GameComponent{
	private float height;
	String label;
	
	//constructor
	public GameButtonLabel(GameStage gameStage, MainApplet parent, float x, float y) {
		super(gameStage, parent, x, y);
		this.setLabel("");
		this.height = 30;
	}
	
	//display label
	@Override
	public void display() {
		//display label
		if(!this.label.equals("")){
			super.setPosition(super.getParent().mouseX, super.getParent().mouseY - this.height/2);
			super.getParent().stroke(0);
			super.getParent().fill(255);
			super.getParent().rect(super.getX(), super.getY(), super.getParent().textWidth(this.label) + 20, this.height);
			super.getParent().fill(0);
			super.getParent().textSize(this.height/1.8f);
			super.getParent().textAlign(PApplet.LEFT, PApplet.CENTER);
			super.getParent().text(this.label, super.getParent().mouseX + 10, super.getParent().mouseY);
			super.getParent().textAlign(PApplet.CENTER, PApplet.CENTER);
		}
		
		//set label text
		if(super.getGameStage().getArcherButton().mouseOn()){
			this.setLabel("Cost: " + this.getGameStage().getMyArcherHireCost());
		}else if(super.getGameStage().getArcherButton().mouseOnUpgrade()){
			this.setLabel("Cost: " + this.getGameStage().getMyArcherUpgradeCost());
		}else if(super.getGameStage().getWarriorButton().mouseOn()){
			this.setLabel("Cost: " + this.getGameStage().getMyWarriorHireCost());
		}else if(super.getGameStage().getWarriorButton().mouseOnUpgrade()){
			this.setLabel("Cost: " + this.getGameStage().getMyWarriorUpgradeCost());
		}else{
			this.setLabel("");
		}
	}
	
	//setter
	public void setLabel(String txt){ this.label = txt;	}
}