package main.server;

public class Archer extends Soldier {
	public Archer(GameStatus gs, int ID) {
		super(gs, ID, SoldierType.archer, 3, 70, 4);
	}
}
