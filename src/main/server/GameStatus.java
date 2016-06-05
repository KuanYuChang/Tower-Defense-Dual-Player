package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.TreeMap;

import javax.swing.SwingUtilities;

public class GameStatus extends Thread{
	//server
	ServerMain parent;
	
	//game elements
	private final int baseAmount = 10;
	private final int baseWarriorAmount = 2;
	private final int baseArcherAmount = 3;
	private int money, maxMoney;
	private int towerHP, towerMaxHP;
	private int warriorLvl, archerLvl;
	private int warriorHireCost, warriorUpgradeCost;
	private int archerHireCost, archerUpgradeCost;
	private TreeMap<Integer, Soldier> soldiers;
	private int soldierCounter;
	private boolean isPlaying;
	
	//communicate with client
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	//enemy
	private GameStatus enemy;
	
	//constructor
	public GameStatus(ServerMain parent, Socket socket){
		//initial variable
		this.parent = parent;
		this.warriorLvl = this.archerLvl = this.money = 0;
		this.towerHP = this.towerMaxHP = this.maxMoney = this.baseAmount;
		this.warriorHireCost = this.baseWarriorAmount;
		this.warriorUpgradeCost = this.baseWarriorAmount * 3;
		this.archerHireCost = this.baseArcherAmount;
		this.archerUpgradeCost = this.baseArcherAmount * 3;
		this.soldiers = new TreeMap<Integer, Soldier>();
		this.isPlaying = true;
		
		//setup socket
		try{
			this.socket = socket;
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//send basic information to client
		this.sendMaxMoney();
		this.sendTowerHP();
		this.sendTowerMaxHP();
		this.sendWarriorHireCost();
		this.sendWarriorUpgradeCost();
		this.sendArcherHireCost();
		this.sendArcherUpgradeCost();
	}
	
	//run forever
	public void run(){
		while(this.isPlaying){
			try {
				String line = GameStatus.this.reader.readLine();
				GameStatus.this.decode(line);
			} catch (IOException e) {
				//e.printStackTrace();
				break;
			}
		}
	}
	
	//check money before decrease money 
	private boolean decreaseMoney(int amount){
		if((this.money-amount) >= 0){
			this.money -= amount;
			this.sendMoney();
			return true;
		}else{
			this.sendNoEnoughMoney();
			return false;
		}
	}
	
	//add money by default
	public void addMoney() {
		this.addMoney(this.warriorLvl + this.archerLvl + 1);
	}
	
	//add money by amount
	public void addMoney(int amount){
		if((this.money + amount) <= this.maxMoney){
			this.money += amount;
		}else{
			this.money = this.maxMoney;
		}
		this.sendMoney();
	}
	
	//send message to client
	public void sendMessage(String message) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				GameStatus.this.writer.println(message);
				GameStatus.this.writer.flush();
			}
		});
	}
	
	//setup enemy
	public void addEnemy(GameStatus gs){
		this.enemy = gs;
		this.sendMessage("EnemyTowerHP-" + this.enemy.getTowerHP());
		this.sendMessage("EnemyTowerMaxHP-" + this.enemy.getTowerMaxHP());
	}
	
	//decode information from client
	public void decode(String msg){
		String op = msg.split("-")[0];
		if(op.equals("Hire")){
			this.hireSoldier(msg.split("-")[1]);
		}else if(op.equals("Upgrade")){
			this.upgradeSoldier(msg.split("-")[1]);
		}else if(msg.equals("Correct")){
			this.addMoney(this.maxMoney/5);
		}
	}
	
	//hire soldier
	private void hireSoldier(String msg) {
		this.soldierCounter++;
		if(msg.equals("Warrior")){
			if(this.decreaseMoney(this.warriorHireCost)){
				Soldier warrior = new Warrior(this, this.soldierCounter);
				this.soldiers.put(this.soldierCounter, warrior);
			}
		}else if(msg.equals("Archer")){
			if(this.decreaseMoney(this.archerHireCost)){
				Soldier archer = new Archer(this, this.soldierCounter);
				this.soldiers.put(this.soldierCounter, archer);
			}
		}
	}
	
	//upgrade soldier
	private void upgradeSoldier(String msg) {
		if(msg.equals("Warrior")){
			if(this.decreaseMoney(this.warriorUpgradeCost)){
				this.towerUpgrade();
				this.maxMoneyUpgrade();
				this.warriorUpgrade();
			}
		}else if(msg.equals("Archer")){
			if(this.decreaseMoney(this.archerUpgradeCost)){
				this.towerUpgrade();
				this.maxMoneyUpgrade();
				this.archerUpgrade();
			}
		}
	}
	
	//upgrade tower
	private void towerUpgrade(){
		this.towerHP += this.baseAmount;
		this.sendTowerHP();
		this.enemy.sendEnemyTowerHP();
		
		this.towerMaxHP += this.baseAmount;
		this.sendTowerMaxHP();
		this.enemy.sendEnemyTowerMaxHP();
	}
	
	//upgrade amount of maximum of money
	private void maxMoneyUpgrade(){
		this.maxMoney += this.baseAmount;
		this.sendMaxMoney();
	}
	
	//upgrade warrior
	private void warriorUpgrade(){
		this.warriorLvl++;
		this.sendWarriorLvl();
		this.enemy.sendEnemyWarriorLvl();
		
		this.warriorHireCost += this.baseWarriorAmount;
		this.sendWarriorHireCost();
		
		this.warriorUpgradeCost += this.baseWarriorAmount*3;
		this.sendWarriorUpgradeCost();
		
		for(Integer soldierID: this.soldiers.keySet()){
			if(this.soldiers.get(soldierID).getType() == SoldierType.warrior){
				this.soldiers.get(soldierID).upgrade();
			}
		}
	}
	
	//upgrade archer
	private void archerUpgrade(){
		this.archerLvl++;
		this.sendArcherLvl();
		this.enemy.sendEnemyArcherLvl();
		
		this.archerHireCost += this.baseArcherAmount;
		this.sendArcherHireCost();
		
		this.archerUpgradeCost += this.baseArcherAmount*3;
		this.sendArcherUpgradeCost();
		
		for(Integer soldierID: this.soldiers.keySet()){
			if(this.soldiers.get(soldierID).getType() == SoldierType.archer){
				this.soldiers.get(soldierID).upgrade();
			}
		}
	}
	
	//tower injury
	public void towerInjury(int damage) {
		if((this.towerHP - damage) <= 0){
			this.towerHP = 0;
		}
		else{
			this.towerHP -= damage;
		}
		this.sendTowerHP();
		this.getEnemy().sendEnemyTowerHP();
		if(this.towerHP <= 0){
			this.sendMessage("Lose");
			this.gameOver();
			this.getEnemy().sendMessage("Win");
			this.getEnemy().gameOver();
			this.parent.reset();
		}
	}
	
	//set playing flag when game over
	public void gameOver(){ this.isPlaying = false; }
	public boolean isRunning(){ return this.isPlaying; }

	//getter
	public int getTowerHP()					{ return this.towerHP; }
	public int getTowerMaxHP()				{ return this.towerMaxHP; }
	public int getWarriorLvl()				{ return this.warriorLvl; }
	public int getArcherLvl()				{ return this.archerLvl;}
	public GameStatus getEnemy()			{ return this.enemy; }
	public TreeMap<Integer, Soldier> getSoldiers()	{ return this.soldiers; }
	
	//send status
	private void sendWarriorLvl()			{ this.sendMessage("WarriorLvl-" + this.warriorLvl);}
	private void sendWarriorHireCost()		{ this.sendMessage("WarriorHireCost-" + this.warriorHireCost);}
	private void sendWarriorUpgradeCost()	{ this.sendMessage("WarriorUpgradeCost-" + this.warriorUpgradeCost);}
	private void sendArcherLvl()			{ this.sendMessage("ArcherLvl-" + this.archerLvl);}
	private void sendArcherHireCost()		{ this.sendMessage("ArcherHireCost-" + this.archerHireCost);}
	private void sendArcherUpgradeCost()	{ this.sendMessage("ArcherUpgradeCost-" + this.archerUpgradeCost);}
	private void sendNoEnoughMoney()		{ this.sendMessage("NoEnoughMoney");}
	private void sendMoney()				{ this.sendMessage("Money-" + this.money);}
	private void sendMaxMoney()				{ this.sendMessage("MaxMoney-" + this.maxMoney);}
	private void sendTowerHP()				{ this.sendMessage("TowerHP-" + this.towerHP);}
	private void sendTowerMaxHP()			{ this.sendMessage("TowerMaxHP-" + this.towerMaxHP);}
	public void sendEnemyTowerHP()			{ this.sendMessage("EnemyTowerHP-" + this.enemy.getTowerHP());}
	public void sendEnemyTowerMaxHP()		{ this.sendMessage("EnemyTowerMaxHP-" + this.enemy.getTowerMaxHP());}
	public void sendEnemyWarriorLvl()		{ this.sendMessage("EnemyWarriorLvl-" + this.enemy.getWarriorLvl());}
	public void sendEnemyArcherLvl()		{ this.sendMessage("EnemyArcherLvl-" + this.enemy.getArcherLvl());}
}
