package main.client;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private final int width = 1000;
	private final int height = 500;
	private ClientState state;
	
	//layout
	private GameMenu menu;
	private GameStage gameStage;
	private GameManual manual;
	private GameManualSoldier manual_soldier;
	private GameList list;

	//setup
	@Override
	public void setup() {
		this.size(this.width, this.height);
		Ani.init(this);
		this.state = ClientState.menu;
		this.menu = new GameMenu(this);
		this.manual = new GameManual(this);
		this.manual_soldier = new GameManualSoldier(this);
		this.list = new GameList(this);
		this.ellipseMode(RADIUS);
		this.textAlign(CENTER, CENTER);
		this.textFont(this.createFont("Segoe UI Black", 32));
		this.smooth();		
	}
	
	//draw components
	@Override
	public void draw() {
		this.background(255);
		if(this.state == ClientState.menu){
			this.menu.display();
		}else if(this.state == ClientState.playing){
			this.gameStage.display();
		}else if(this.state == ClientState.manual){
			this.manual.display();
		}else if(this.state == ClientState.manual_soldier){
			this.manual_soldier.display();
		}else if(this.state == ClientState.list){
			this.list.display();
		}
	}
	 
	//called after a mouse button has been pressed and then released
	public void mouseClicked(){
		if(this.state == ClientState.menu){
			
		}else if(this.state == ClientState.list){
			
		}else if(this.state == ClientState.manual){
			
		}else if(this.state == ClientState.manual_soldier){
			
		}else if(this.state == ClientState.playing){
			
		}
	}
	
	public void reset(){
		this.state = ClientState.menu;
		//this.gameStage = null;
	}
	
	/*
	//load data
	private void loadData(){
	}
	*/
}
