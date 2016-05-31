package main.client;

public abstract class GameLayout {
	private MainApplet parent;
	
	//constructor
	public GameLayout(MainApplet parent){
		this.parent = parent;
	}
	
	//getter
	public MainApplet getParent(){ return this.parent; }
	
	//method need to implement
	public abstract void display();
}
