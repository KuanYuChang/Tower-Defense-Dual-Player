package main.server;

public class Warrior extends Soldier {	
	public Warrior(GameStatus gs, int ID) {
		super(gs, ID, SoldierType.warrior, 5, 25, 2);
	}
}
