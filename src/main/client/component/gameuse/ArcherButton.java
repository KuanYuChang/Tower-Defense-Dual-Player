package main.client.component.gameuse;

import main.client.MainApplet;
import main.client.layout.GameStage;

public class ArcherButton extends SoldierButton{
	private MyArcher archer;
	
	//constructor
	public ArcherButton(GameStage gameStage, MainApplet parent, float x, float y, float width, float height) {
		super(gameStage, parent, x, y, width, height);
		archer = new MyArcher(super.getGameStage(), parent, x, y-height/10);
	}
	
	//display button
	public void display(){
		super.display();
		super.getParent().fill(0);
		this.getParent().text("Level " + super.getGameStage().getMyArcherLvl(), 
				super.getX(), super.getY() - super.getHeight()/2.5f);
		this.archer.display();
		this.archer.attack();
	}	
}