package main.client;

public class GameList extends GameLayout {
	private final String title = "Vocabulary List";
	//private Button returnBtn;

	public GameList(MainApplet parent) {
		super(parent);
	}

	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().text(this.title, 500, 50);
	}
}
