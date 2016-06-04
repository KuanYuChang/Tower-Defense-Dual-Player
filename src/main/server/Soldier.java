package main.server;

public abstract class Soldier{
	private GameStatus gameStatus;
	private int ID;
	private int baseHP, HP;
	private int pos;
	private boolean isAlive, movable;
	private SoldierType type;
	private int attackDistance;
	private int baseDamage, damage;
	private Thread moveThread, attackThread;
	
	//constructor
	public Soldier(GameStatus gs, int ID, SoldierType type, int HP, int attackDistance, int damage){
		this.gameStatus = gs;
		this.ID = ID;
		this.baseHP = HP;
		this.baseDamage = damage;
		this.attackDistance = attackDistance;
		this.type = type;
		this.isAlive = true;
		this.movable = true;
		this.setPos(-40);
		int multiple = 1;
		if(this.type == SoldierType.warrior){
			multiple = this.gameStatus.getWarriorLvl() + 1;
		}else if(this.type == SoldierType.archer){
			multiple = this.gameStatus.getArcherLvl() + 1;
		}
		this.setHP(this.baseHP * multiple);		
		this.setDamage(this.baseDamage * multiple);
		this.sendAction("Create");
		
		//setup moving thread
		this.moveThread = new Thread(new Runnable(){
			public void run(){
				while(Soldier.this.isAlive && Soldier.this.gameStatus.isRunning()){
					try {
						Thread.sleep(10);
						Soldier enemy = Soldier.this.checkEnemy();
						if(enemy == null && Soldier.this.movable){
							Soldier.this.move();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Soldier.this.sendAction("Dead");
				Soldier.this.gameStatus.getSoldiers().remove(ID);
			}
		});
		
		//setup attacking thread
		this.attackThread = new Thread(new Runnable(){
			public void run(){
				while(Soldier.this.isAlive && Soldier.this.gameStatus.isRunning()){
					try {
						Thread.sleep(10);
						Soldier enemy = Soldier.this.checkEnemy();
						if(enemy != null){
							Soldier.this.movable = false;
							Soldier.this.attack();
							Thread.sleep(1000);
							if(Soldier.this.isAlive){
								enemy.injury(Soldier.this.damage);
							}
						}else if((Soldier.this.pos + Soldier.this.attackDistance) >= 800){
							Soldier.this.movable = false;
							Soldier.this.attack();
							Thread.sleep(1000);
							if(Soldier.this.isAlive){
								Soldier.this.gameStatus.getEnemy().towerInjury(Soldier.this.damage);
							}							
						}						
						Soldier.this.movable = true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		//start thread
		this.moveThread.start();
		this.attackThread.start();
	}
	
	//setter
	public void setHP(int amount){ this.HP = amount; }
	public void setPos(int amount){ this.pos = amount; }
	public void setDamage(int amount){ this.damage = amount; }
	
	//getter
	public int getPos(){ return this.pos; }
	public SoldierType getType(){ return this.type; }
	
	//attack
	public void attack(){
		this.sendAction("Attack");
	}
	
	//move one pixel
	public void move(){
		if(this.pos + this.attackDistance <= 800){
			this.pos++;
			this.sendAction("Move");
		}
	}
	
	//injury when enemy soldier attack
	public void injury(int damage){
		int nextHP = this.HP - damage;
		if(nextHP <= 0){
			this.isAlive = false;
		}else{
			this.setHP(nextHP);
		}
	}
	
	//check if there is an enemy in front of soldier
	private Soldier checkEnemy(){
		for(Integer soldierID: this.gameStatus.getEnemy().getSoldiers().keySet()){
			if((this.gameStatus.getEnemy().getSoldiers().get(soldierID).getPos() + 25 + this.pos + this.attackDistance) >= 800){
				return this.gameStatus.getEnemy().getSoldiers().get(soldierID);
			}
		}
		return null;
	}
	
	//upgrade soldier
	public void upgrade(){
		int nextHP = this.HP + this.baseHP;
		this.setHP(nextHP);
		int nextDamage = this.damage + this.baseDamage;
		this.setDamage(nextDamage);
	}
	
	//send information to both client
	public void sendAction(String action){
		this.sendActionToClient(action);
		this.sendActionToEnemy(action);
	}
	
	//send information to client
	public void sendActionToClient(String action){
		String msg = "";
		if(this.type == SoldierType.warrior){
			msg += "Warrior-";
		}else if(this.type == SoldierType.archer){
			msg += "Archer-";
		}
		msg += this.ID + "-" + action;		
		this.gameStatus.sendMessage(msg);
	}
	
	//send information to enemy client
	public void sendActionToEnemy(String action){
		String msg = "";
		if(this.type == SoldierType.warrior){
			msg += "EnemyWarrior-";
		}else if(this.type == SoldierType.archer){
			msg += "EnemyArcher-";
		}
		msg += this.ID + "-" + action;
		this.gameStatus.getEnemy().sendMessage(msg);
	}
}
