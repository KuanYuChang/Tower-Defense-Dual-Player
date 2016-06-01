package main.client;

import processing.core.PApplet;

public class GameManual extends GameLayout {
	private final String title = "Game Manual";
	private Button soliderBtn, returnBtn;
	
	//constructor
	public GameManual(MainApplet parent) {
		super(parent);
		this.soliderBtn = new Button(super.getParent(), "Soldier", 570, 420, 250, 50);
		this.returnBtn = new Button(super.getParent(), "Return", 850, 420, 250, 50);
	}
	
	//getter
	public Button getSoldierButton(){ return this.soliderBtn; }
	public Button getReturnButton(){ return this.returnBtn; }
	
	//display component
	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().text(this.title, 500, 50);
		super.getParent().textSize(35);
		super.getParent().textAlign(PApplet.LEFT, PApplet.CENTER);
		super.getParent().text("Hire soldiers to attack enemy's tower.", 50, 150);
		super.getParent().text("Answering volcab. quiz right could gain more golds.", 50, 240);
		super.getParent().text("GameOver when one's tower's HP turns to zero", 50, 330);
		super.getParent().textAlign(PApplet.CENTER, PApplet.CENTER);
		this.soliderBtn.display();
		this.returnBtn.display();
	}
}
