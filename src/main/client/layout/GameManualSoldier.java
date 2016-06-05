package main.client.layout;

import main.client.MainApplet;
import main.client.component.Button;
import main.client.component.MyArcherDisplay;
import main.client.component.MySoldierDisplay;
import main.client.component.MyWarriorDisplay;
import main.client.component.RadarChart;

public class GameManualSoldier extends GameLayout {
	private final String title = "Game Manual";
	private MySoldierDisplay warriorDisplay, archerDisplay;
	private RadarChart warriorInfo, archerInfo, nullInfo;
	private Button returnBtn;
	
	//constructor
	public GameManualSoldier(MainApplet parent) {
		super(parent);
		this.warriorDisplay = new MyWarriorDisplay(super.getParent(), 150, 200);
		this.archerDisplay = new MyArcherDisplay(super.getParent(), 150, 300);
		this.warriorInfo = new RadarChart(super.getParent(), 450, 300, 5, 25, 2);
		this.archerInfo = new RadarChart(super.getParent(), 450, 300, 3, 70, 4);
		this.nullInfo = new RadarChart(super.getParent(), 450, 300, 0, 0, 0);
		this.returnBtn = new Button(super.getParent(), "Return", 850, 420, 250, 50);
	}
	
	//getter
	public Button getReturnButton(){ return this.returnBtn; }
	
	//display component
	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().text(this.title, 500, 50);
		this.warriorDisplay.display();
		this.archerDisplay.display();
		if(this.warriorDisplay.mouseOn()){
			this.warriorInfo.display();
		}else if(this.archerDisplay.mouseOn()){
			this.archerInfo.display();
		}else{
			this.nullInfo.display();
		}
		this.returnBtn.display();
	}
}