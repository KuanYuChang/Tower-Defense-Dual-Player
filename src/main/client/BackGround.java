package main.client;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import processing.core.PApplet;

public class BackGround {
	private GameStage gameStage;
	private MainApplet parent;
	private float myX, enemyX, y;
	private int noMoney;
	private AniSequence noMoneyAni;
	
	//constructor
	public BackGround(GameStage gameStage, MainApplet parent, float myX, float enemyX, float y){
		this.gameStage = gameStage;
		this.parent = parent;
		this.myX = myX;
		this.enemyX = enemyX;
		this.y = y;
		
		//construct animation for no enough money
		this.noMoneyAni = new AniSequence(this.parent);
		this.noMoneyAni.beginSequence();
		for(int i = 0; i < 3; i++){
			this.noMoneyAni.add(Ani.to(this, 0.05f, "noMoney", 255));
			this.noMoneyAni.add(Ani.to(this, 0.05f, "noMoney", 255));
			this.noMoneyAni.add(Ani.to(this, 0.05f, "noMoney", 0));
		}
		this.noMoneyAni.endSequence();
	}
	
	//display background
	public void disaply(){
		this.parent.background(255);
		
		//display money
		this.parent.textSize(20);
		this.parent.fill(this.noMoney, 0, 0);
		this.parent.textAlign(PApplet.RIGHT, PApplet.CENTER);
		this.parent.text(this.gameStage.getMoney() + "/" + this.gameStage.getMaxMOney() + " $", 980, 20);
		this.parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		
		//display line
		this.parent.strokeWeight(2);
		this.parent.stroke(0);
		this.parent.line(0, this.y-50, this.enemyX-80, this.y-50);
		this.parent.line(this.enemyX, this.y-50, this.myX, this.y-50);
		this.parent.line(this.myX+80, this.y-50, 1000, this.y-50);
		
		//display my tower
		this.parent.fill(0);
		this.parent.textSize(15);
		this.parent.text(this.gameStage.getMyTowerHP() + " / " + this.gameStage.getMyTowerMaxHP(), 
				this.myX + 40, this.y - 120);
		this.parent.fill(0, 0, 255, 
				this.gameStage.getMyArcherLvl() + this.gameStage.getMyWarriorLvl());
		this.parent.stroke(0, 0, 255);
		this.parent.strokeWeight(2);		
		this.parent.rect(this.myX-40 + 40, this.y-80, 80, 80);
		this.parent.rect(this.myX-50 + 40, this.y-95, 100, 15);
		this.parent.fill(255);
		this.parent.arc(this.myX + 40, this.y-15, 30, 30, 
				PApplet.PI/6*5, PApplet.PI/6*(5+8), PApplet.CHORD);
		
		//display enemy tower
		this.parent.fill(0);
		this.parent.textSize(15);
		if(this.gameStage.getEnemyTowerMaxHP() == 0){
			this.parent.text("waiting...", this.enemyX - 40, this.y - 140);
		}
		this.parent.text(this.gameStage.getEnemyTowerHP() + " / " + this.gameStage.getEnemyTowerMaxHP(), 
				this.enemyX - 40, this.y - 120);
		this.parent.fill(255, 0, 0, 
				this.gameStage.getEnemyArcherLvl() + this.gameStage.getEnemyWarriorLvl());
		this.parent.stroke(255, 0, 0);
		this.parent.strokeWeight(2);		
		this.parent.rect(this.enemyX-40 - 40, this.y-80, 80, 80);
		this.parent.rect(this.enemyX-50 - 40, this.y-95, 100, 15);
		this.parent.fill(255);
		this.parent.arc(this.enemyX - 40, this.y-15, 30, 30, 
				PApplet.PI/6*5, PApplet.PI/6*(5+8), PApplet.CHORD);
	}
	
	//display noMoney animation
	public void noEnoughMoney(){
		if(!this.noMoneyAni.isPlaying())
			this.noMoneyAni.start();
		else if(this.noMoneyAni.isEnded())
			this.noMoneyAni.start();
	}
}