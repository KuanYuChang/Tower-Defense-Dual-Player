package main.client.vocaquiz;

import main.client.component.DetectMouse;

public class WordOption implements DetectMouse {
	private float width, height;
	private float x, y;
	private VocaGame parent;
	private String label;
	private boolean isCorrect, isWrong;
	
	//constructor
	public WordOption(VocaGame parent, String label, float x, float y, float width, float height) {
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.reset(label);
	}
	
	//display
	public void display() {
		if(this.isCorrect){
			this.parent.fill(0, 0, 255);
			this.parent.stroke(0, 0, 255);
		}else if(this.isWrong){
			this.parent.fill(255, 0, 0);
			this.parent.stroke(255, 0, 0);
		}else{
			this.parent.fill(0);
			this.parent.stroke(0);
		}
		this.parent.textSize(this.height/1.8f);
		this.parent.text(this.label, this.x, this.y);
		this.parent.strokeWeight(2);
		this.parent.noFill();
		if(this.mouseOn()){
			this.parent.rect((this.x-this.width/2), (this.y-this.height/2), this.width, this.height);
		}
	}
	
	//detect mouse hovering
	@Override
	public boolean mouseOn() {
		if(this.parent.mouseX >= this.x-this.width/2
		&& this.parent.mouseX <= this.x+this.width/2
		&& this.parent.mouseY >= this.y-this.height/2
		&& this.parent.mouseY <= this.y+this.height/2){
			return true;
		}else{
			return false;
		}
	}
	
	//getter
	public String getLable(){ return this.label; }
	
	public void reset(String label){
		this.label = label;
		this.isCorrect = false;
		this.isWrong = false;
	}
	
	public void showCorrect(){
		this.isCorrect = true;
	}
	
	public void showWrong(){
		this.isWrong = true;
	}
}

