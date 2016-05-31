package main.client;

public class GameMenu extends GameLayout{
	private final String title = "Tower Defense";
	//private Button list, manual, start;
	
	//constructor
	public GameMenu(MainApplet parent) {
		super(parent);
	}
	
	//display menu
	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().text(this.title, 500, 50);
	}
	
	//detect where mouse is hovering
	/*
	public boolean mouseOnStart()	{ }
	public boolean mouseOnManual()	{ }
	public boolean mouseOnList()	{ }
	*/
}