package model;

import game.Game;
import observer.Publisher;
import observer.Subscriber;

public class Monster extends Publisher {

	private char code;
	private String name;
	private int speed;
	private int health;
	private int armor;
	private int drop;
	
	private int count;

	public Monster(char code, String name, int speed, int health, int armor, int drop) {
		super();
		this.code = code;
		this.name = name;
		this.speed = speed;
		this.health = health;
		this.armor = armor;
		this.drop = drop;
		this.count = 0;
	}

	public char getCode() {
		return code;
	}

	public void setCode(char code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getDrop() {
		return drop;
	}

	public void setDrop(int drop) {
		this.drop = drop;
	}
	
	public boolean isTimeToMove() {
		if (count >= speed) {
			count = 0;
			return true;
		}
		return false;
	}

	@Override
	public void notifySubscribers(Tile fromTile) {
		for (Subscriber subscriber : subscribers) {
			if (Game.getInstance().isMonsterInTowerRange(fromTile, (Tower) subscriber)) {				
				subscriber.fire(this);
			}
		}
	}

	public void performTick() {
		count++;
	}

}
