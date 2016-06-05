package main.client;

import java.util.TreeMap;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SubWindow extends JFrame{
	private final int width = 300;
	private final int height = 400;
	private VocaGame vocaGame;
	
	public SubWindow(GameStage gameStage, TreeMap<String, TreeMap<String, String>> lists){
		super("Vocabulary Quiz");
		
		this.vocaGame = new VocaGame(gameStage, lists);
		this.vocaGame.init();
		this.vocaGame.start();
		this.vocaGame.setFocusable(true);
		
		super.setContentPane(this.vocaGame);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(this.width, this.height);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void close(){
		this.vocaGame.destroy();
		this.setVisible(false);
		this.dispose();
	}
}
