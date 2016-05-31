package main.client;

public class GameManualSoldier extends GameLayout {
	private final String title = "Game Manual";
	/*
	private MySoldierDisplay warriorDisplay, archerDisplay;
	private RadarChart warriorInfo, archerInfo, nullInfo;
	private Button returnBtn;
	*/
	
	//constructor
	public GameManualSoldier(MainApplet parent) {
		super(parent);
	}
	
	//display component
	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().text(this.title, 500, 50);
	}
}