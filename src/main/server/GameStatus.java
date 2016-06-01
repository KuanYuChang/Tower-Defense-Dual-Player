package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.TreeMap;

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

	public GameStatus(ServerMain parent, Socket socket) {
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
	}
	
	//run forever
	public void run(){
		while(this.isPlaying){
			try {
				String line = GameStatus.this.reader.readLine();
				GameStatus.this.decode(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//check money before decrease money 
	private boolean decreaseMoney(int amount){
		if((this.money-amount) >= 0){
			this.money -= amount;
			//send info. of money to client
			return true;
		}else{
			//send no enough money info. to client
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
		//send info. of money to client
	}

	//setup enemy
	public void addEnemy(GameStatus gs){
		this.enemy = gs;
		//send enemy info. to client
	}

	//decode information from client
	public void decode(String msg){
		String op = msg.split("-")[0];
		if(op.equals("Hire")){
			//Hire soldier
		}else if(op.equals("Upgrade")){
			//Upgrade soldier
		}
	}
	
	//hire / upgrade soldier
	//hire soldier
	private void hireSoldier(String msg) {
		if(msg.equals("Warrior")){
			//warrior
		}else if(msg.equals("Archer")){
			//archer
		}
	}
	
	//upgrade soldier
	private void upgradeSoldier(String msg) {
		if(msg.equals("Warrior")){
			//warrior
		}else if(msg.equals("Archer")){
			//archer
		}
	}
	
	//getter
	public int getTowerHP()					{ return this.towerHP; }
	public int getTowerMaxHP()				{ return this.towerMaxHP; }
	public int getWarriorLvl()				{ return this.warriorLvl; }
	public int getArcherLvl()				{ return this.archerLvl;}
	public GameStatus getEnemy()			{ return this.enemy; }
	public TreeMap<Integer, Soldier> getSoldiers()	{ return this.soldiers; }
}
