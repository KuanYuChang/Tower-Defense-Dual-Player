package main.client;

public abstract class GameComponent extends Component {
	private GameStage gameStage;
	
	//constructor
	public GameComponent(GameStage gameStage, MainApplet parent, float x, float y) {
		super(parent, x, y);
		this.gameStage = gameStage;
	}
	
	//getter
	public GameStage getGameStage(){ return this.gameStage; }
	
	//method need to implement
	@Override
	public abstract void display();
}