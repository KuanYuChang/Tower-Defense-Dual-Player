package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameStage extends GameLayout{
	//communicate with server
	private Socket clientSocket;
	private String destinationIPAddr;
	private int destinationPortNum;
	private PrintWriter writer;
	private BufferedReader reader;
	private Thread thread;
	
	//game GUI
	private BackGround bg;
	private WarriorButton warriorButton;
	private ArcherButton archerButton;
	private GameButtonLabel gameButtonLabel;
	
	//game status
	private int money, maxMoney;
	private int warriorHireCost, warriorUpgradeCost;
	private int archerHireCost, archerUpgradeCost;
	private int eWarriorLvl, eArcherLvl;
	private int mWarriorLvl, mArcherLvl;
	private int eTowerHP, eTowerMaxHP, mTowerHP, mTowerMaxHP;
	private boolean isRunning;
	
	//characters
	private TreeMap<Integer, MySoldier> mySoldiers;
	private TreeMap<Integer, EnemySoldier> enemySoldiers;
	
	//others
	private Random randomGen;
	
	//constructor
	public GameStage(MainApplet parent){
		super(parent);
		this.bg = new BackGround(this, super.getParent(), 900, 100, 250);
		this.warriorButton = new WarriorButton(this, super.getParent(), 333, 400, 120, 120);
		this.archerButton = new ArcherButton(this, super.getParent(), 666, 400, 120, 120);
		this.mySoldiers = new TreeMap<Integer, MySoldier>();
		this.enemySoldiers = new TreeMap<Integer, EnemySoldier>();
		this.gameButtonLabel = new GameButtonLabel(this, super.getParent(), 0, 0);
		this.randomGen = new Random();
		this.isRunning = true;
	}
	
	//display components
	public void display(){
		this.bg.disaply();		
		this.warriorButton.display();
		this.archerButton.display();
		for(Integer soldierID: this.mySoldiers.keySet())
			{ try{ this.mySoldiers.get(soldierID).display();}catch(Exception e){break;}}
		for(Integer soldierID: this.enemySoldiers.keySet())
			{ try{ this.enemySoldiers.get(soldierID).display();}catch(Exception e){break;}}
		super.getParent().stroke(0);
		super.getParent().strokeWeight(2);
		super.getParent().line(0, 320, 1000, 320);	
		this.gameButtonLabel.display();
	}
	
	//decode information from server
	private void decode(String msg){
		String op = msg.split("-")[0];
		if(op.equals("Money")){
			this.money = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("MaxMoney")){
			this.maxMoney = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("NoEnoughMoney")){
			this.bg.noEnoughMoney();
		}else if(op.equals("TowerHP")){
			this.mTowerHP = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("TowerMaxHP")){
			this.mTowerMaxHP = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("EnemyTowerHP")){
			this.eTowerHP = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("EnemyTowerMaxHP")){
			this.eTowerMaxHP = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("WarriorLvl")){
			this.mWarriorLvl = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("WarriorHireCost")){
			this.warriorHireCost = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("WarriorUpgradeCost")){
			this.warriorUpgradeCost = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("ArcherLvl")){
			this.mArcherLvl = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("ArcherHireCost")){
			this.archerHireCost = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("ArcherUpgradeCost")){
			this.archerUpgradeCost = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("EnemyWarriorLvl")){
			this.eWarriorLvl = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("EnemyArcherLvl")){
			this.eArcherLvl = Integer.parseInt(msg.split("-")[1]);
		}else if(op.equals("Warrior")){
			int ID = Integer.parseInt(msg.split("-")[1]);
			if(msg.split("-")[2].equals("Create")){
				this.mySoldiers.put(ID, new MyWarrior(this, super.getParent(), 900+40, 250 + this.randomGen.nextInt(25)));
			}else if(msg.split("-")[2].equals("Attack")){
				try{this.mySoldiers.get(ID).attack();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Move")){
				try{this.mySoldiers.get(ID).move();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Dead")){
				this.mySoldiers.get(ID).setPosition(-1000, -1000);
			}
		}else if(op.equals("Archer")){
			int ID = Integer.parseInt(msg.split("-")[1]);
			if(msg.split("-")[2].equals("Create")){
				this.mySoldiers.put(ID, new MyArcher(this, super.getParent(), 900+40, 250 + this.randomGen.nextInt(25)));
			}else if(msg.split("-")[2].equals("Attack")){
				try{this.mySoldiers.get(ID).attack();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Move")){
				try{this.mySoldiers.get(ID).move();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Dead")){
				this.mySoldiers.get(ID).setPosition(-1000, -1000);
			}
		}else if(op.equals("EnemyWarrior")){
			int ID = Integer.parseInt(msg.split("-")[1]);
			if(msg.split("-")[2].equals("Create")){
				this.enemySoldiers.put(ID, new EnemyWarrior(this, super.getParent(), 100-40, 250 + this.randomGen.nextInt(25)));
			}else if(msg.split("-")[2].equals("Attack")){
				try{this.enemySoldiers.get(ID).attack();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Move")){
				try{this.enemySoldiers.get(ID).move();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Dead")){
				this.enemySoldiers.get(ID).setPosition(-1000, -1000);
			}
		}else if(op.equals("EnemyArcher")){
			int ID = Integer.parseInt(msg.split("-")[1]);
			if(msg.split("-")[2].equals("Create")){
				this.enemySoldiers.put(ID, new EnemyArcher(this, super.getParent(), 100-40, 250 + this.randomGen.nextInt(25)));
			}else if(msg.split("-")[2].equals("Attack")){
				try{this.enemySoldiers.get(ID).attack();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Move")){
				try{this.enemySoldiers.get(ID).move();}catch(Exception e){System.out.println(msg);}
			}else if(msg.split("-")[2].equals("Dead")){
				this.enemySoldiers.get(ID).setPosition(-1000, -1000);
			}
		}else if(op.equals("Win")){
			JOptionPane.showMessageDialog(new JFrame(), "You win the game!");
			this.isRunning = false;
			GameStage.super.getParent().reset();
		}else if(op.equals("Lose")){
			JOptionPane.showMessageDialog(new JFrame(), "You lose the game...");
			this.isRunning = false;
			GameStage.super.getParent().reset();
		}
	}
	
	//connect to server
	public void connect(){
		try{
			//setup new socket
			this.destinationIPAddr = JOptionPane.showInputDialog("Server IP address", "127.0.0.1");
			this.destinationPortNum = 9527;
			this.clientSocket = new Socket(this.destinationIPAddr, this.destinationPortNum);
			this.writer = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			thread = new Thread(new Runnable(){
				@Override
				public void run() {
					while(GameStage.this.isRunning){
						try {
							String line = GameStage.this.reader.readLine();
							GameStage.this.decode(line);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			thread.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//send message to server
	private void sendMessage(String message){
		this.writer.println(message);
		this.writer.flush();
	}
	
	//getter
	public BackGround getBackGround()		{ return this.bg; }
	public int getEnemyTowerHP()			{ return this.eTowerHP; }
	public int getEnemyTowerMaxHP()			{ return this.eTowerMaxHP; }
	public int getEnemyWarriorLvl()			{ return this.eWarriorLvl; }
	public int getEnemyArcherLvl()			{ return this.eArcherLvl; }
	public int getMyTowerHP()				{ return this.mTowerHP; }
	public int getMyTowerMaxHP()			{ return this.mTowerMaxHP; }
	public int getMyWarriorLvl()			{ return this.mWarriorLvl; }
	public int getMyWarriorHireCost()		{ return this.warriorHireCost; }
	public int getMyWarriorUpgradeCost()	{ return this.warriorUpgradeCost; }
	public int getMyArcherLvl()				{ return this.mArcherLvl; }
	public int getMyArcherHireCost()		{ return this.archerHireCost; }
	public int getMyArcherUpgradeCost()		{ return this.archerUpgradeCost; }
	public int getMoney()					{ return this.money; }
	public int getMaxMOney()				{ return this.maxMoney; }
	public WarriorButton getWarriorButton()	{ return this.warriorButton; }
	public ArcherButton getArcherButton()	{ return this.archerButton; }
	
	//control
	public void hireWarrior()		{ this.sendMessage("Hire-Warrior"); }
	public void upgradeWarrior()	{ this.sendMessage("Upgrade-Warrior"); }
	public void hireArcher()		{ this.sendMessage("Hire-Archer"); }
	public void upgradeArcher()		{ this.sendMessage("Upgrade-Archer"); }
	public void answerCorrect()		{ this.sendMessage("Correct"); }
}