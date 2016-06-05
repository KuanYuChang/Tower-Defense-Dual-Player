package main.client.component;

import main.client.MainApplet;

public class Button extends Component implements DetectMouse{
	private float width, height;
	private String label;
	
	//constructor
	public Button(MainApplet parent, String label, float x, float y, float width, float height){
		super(parent, x, y);
		this.width = width;
		this.height = height;
		this.label = label;
	}
	
	//display button
	@Override
	public void display(){
		if(this.mouseOn() && !super.getParent().mousePressed){
			super.getParent().stroke(0, 0, 255);
			super.getParent().fill(0, 0, 255);
		}else if(this.mouseOn() && super.getParent().mousePressed){
			super.getParent().stroke(255, 0, 0);
			super.getParent().fill(255, 0, 0);
		}else{
			super.getParent().stroke(0);
			super.getParent().fill(0, 0, 0);
		}
		super.getParent().textSize(this.height/1.8f);
		super.getParent().text(this.label, super.getX(), super.getY());
		super.getParent().strokeWeight(2);
		super.getParent().noFill();
		super.getParent().rect((super.getX()-this.width/2), (super.getY()-this.height/2), this.width, this.height);
	}
	
	//detect mouse hovering
	@Override
	public boolean mouseOn(){		
		if(super.getParent().mouseX >= super.getX()-this.width/2
		&& super.getParent().mouseX <= super.getX()+this.width/2
		&& super.getParent().mouseY >= super.getY()-this.height/2
		&& super.getParent().mouseY <= super.getY()+this.height/2){
			return true;
		}else{
			return false;
		}				
	}
}