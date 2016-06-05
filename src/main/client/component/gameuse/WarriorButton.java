package main.client.component.gameuse;

import main.client.MainApplet;
import main.client.layout.GameStage;

public class WarriorButton extends SoldierButton {
	private MyWarrior warrior;	
	
	//constructor
	public WarriorButton(GameStage gameStage, MainApplet parent, float x, float y, float width, float height) {
		super(gameStage, parent, x, y, width, height);
		this.warrior = new MyWarrior(super.getGameStage(), parent, x, y - height/10);
	}
	
	//display button
	public void display(){
		super.display();
		super.getParent().fill(0);
		super.getParent().text("Level " + super.getGameStage().getMyWarriorLvl(), 
				super.getX(), super.getY() - super.getHeight()/2.5f);
		this.warrior.display();
		this.warrior.attack();
	}
}