package main.client;

import java.util.Iterator;
import java.util.TreeMap;

import processing.core.PApplet;

public class GameVocaList extends GameLayout {
	private final String title = "Vocabulary";
	private TreeMap<String, TreeMap<String, String>> lists;
	private String[] vocaList;
	private String currentDisplay;
	private Button[] swithBtn;
	private Button returnBtn;
	

	public GameVocaList(MainApplet parent, TreeMap<String, TreeMap<String, String>> lists, String[] vocaList) {
		super(parent);
		this.lists = lists;
		this.vocaList = vocaList;
		this.currentDisplay = this.vocaList[0];
		this.swithBtn = new Button[5];
		for(int i = 0; i < 5; i++){
			this.swithBtn[i] = new Button(super.getParent(), this.vocaList[i], 850, 420-(5-i)*60, 250, 50);
		}
		this.returnBtn = new Button(super.getParent(), "Return", 850, 420, 250, 50);
	}
	
	//getter
	public Button getReturnButton(){ return this.returnBtn; }

	@Override
	public void display() {
		super.getParent().fill(0);
		super.getParent().textSize(50);
		super.getParent().textAlign(PApplet.RIGHT, PApplet.CENTER);
		super.getParent().text(this.title, 480, 50);
		super.getParent().textAlign(PApplet.CENTER, PApplet.CENTER);
		super.getParent().text(" - ", 500, 50);
		super.getParent().textAlign(PApplet.LEFT, PApplet.CENTER);
		super.getParent().text(this.currentDisplay, 520, 50);
		super.getParent().textAlign(PApplet.CENTER, PApplet.CENTER);
		this.displayVoca();
		for(Button btn: this.swithBtn){
			btn.display();
		}
		this.returnBtn.display();
	}
	
	private void displayVoca(){
		int maxRow = 8;
		int rowOffset = 40, colOffset = 230;
		int row = 0, col = 0;
		Iterator<String> list = this.lists.get(currentDisplay).keySet().iterator();
		while(list.hasNext()){
			String word = list.next();
			super.getParent().textAlign(PApplet.LEFT, PApplet.CENTER);
			super.getParent().textSize(30);
			super.getParent().text(word, col*colOffset+20, row*rowOffset+120);
			super.getParent().textAlign(PApplet.CENTER, PApplet.CENTER);
			if(row < maxRow-1){
				row++;
			}else{
				row = 0;
				col++;
			}
		}
	}
	
	public void setDisplayVoca(int index){
		this.currentDisplay = this.vocaList[index];
	}
	
	//getter
	public Button[] getSwitchButton(){ return this.swithBtn; }
}
