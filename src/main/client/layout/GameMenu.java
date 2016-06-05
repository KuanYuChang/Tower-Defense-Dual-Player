package main.client.layout;

import main.client.MainApplet;
import main.client.component.Button;

public class GameMenu extends GameLayout{
	private final String title = "Tower Defense";
	private Button list, manual, start;
	
	//constructor
	public GameMenu(MainApplet parent) {
		super(parent);
		this.start = new Button(super.getParent(), "Game Start", 500, 175, 250, 50);
		this.manual = new Button(super.getParent(), "Game Manual", 500, 275, 250, 50);
		this.list = new Button(super.getParent(), "Vocabulary List", 500, 375, 250, 50);
	}
	
	//display menu
	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().text(this.title, 500, 50);
		this.start.display();
		this.manual.display();
		this.list.display();
	}
	
	//detect where mouse is hovering
	public boolean mouseOnStart()	{ return this.start.mouseOn(); }
	public boolean mouseOnManual()	{ return this.manual.mouseOn(); }
	public boolean mouseOnList()	{ return this.list.mouseOn(); }
}