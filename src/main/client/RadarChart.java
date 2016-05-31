package main.client;

import processing.core.PApplet;

public class RadarChart extends Component{
	private final int radius = 150;
	private int HP, attackDistance, damage;
	
	public RadarChart(MainApplet parent, float x, float y, int HP, int attackDistance, int damage) {
		super(parent, x, y);
		this.HP = HP;
		this.attackDistance = attackDistance;
		this.damage = damage;
	}
	
	//display radar chart
	@Override
	public void display() {
		//display background
		super.getParent().strokeWeight(3);
		super.getParent().stroke(0);
		super.getParent().line(super.getX(), super.getY(), 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6), 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6));
		super.getParent().line(super.getX(), super.getY(), 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3), 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3));
		super.getParent().line(super.getX(), super.getY(), 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3*2), 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3*2));
		super.getParent().triangle(
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6), 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6), 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3), 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3), 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3*2), 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3*2));
		super.getParent().strokeWeight(0.5f);
		for(int i = 1; i < 5; i++){
			super.getParent().triangle(
					super.getX() + this.radius*PApplet.cos(PApplet.PI/6)/5*i, 
					super.getY() + this.radius*PApplet.sin(PApplet.PI/6)/5*i, 
					super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3)/5*i, 
					super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3)/5*i, 
					super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3*2)/5*i, 
					super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3*2)/5*i);
		}
		
		//display title
		super.getParent().textSize(20);
		super.getParent().text("Attack Range", 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6)*5/4, 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6)*5/4);
		super.getParent().text("Attak", 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3)*5/4, 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3)*5/4);
		super.getParent().text("HP", 
				super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3*2)*5/4, 
				super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3*2)*5/4);
		
		//display information
		super.getParent().strokeWeight(3);
		super.getParent().stroke(0, 0, 255);
		super.getParent().fill(0, 0, 255, 20);
		super.getParent().triangle(
			super.getX() + this.radius*PApplet.cos(PApplet.PI/6)/100*this.attackDistance, 
			super.getY() + this.radius*PApplet.sin(PApplet.PI/6)/100*this.attackDistance, 
			super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3)/6*this.damage, 
			super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3)/6*this.damage, 
			super.getX() + this.radius*PApplet.cos(PApplet.PI/6+PApplet.TWO_PI/3*2)/6*this.HP, 
			super.getY() + this.radius*PApplet.sin(PApplet.PI/6+PApplet.TWO_PI/3*2)/6*this.HP);
	}
}
