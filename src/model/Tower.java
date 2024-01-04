package model;

import observer.Subscriber;

public abstract class Tower implements Subscriber {

	private char code;
	private String name;
	private int damage;
	private int range;
	private int speed;
	private int price;
	
	private int count;

	public Tower(char code, String name, int damage, int range, int speed, int price) {
		super();
		this.code = code;
		this.name = name;
		this.damage = damage;
		this.range = range;
		this.speed = speed;
		this.price = price;
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

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public void print() {
		System.out.print(name + " [" + price + "]");
	}

	@Override
	public void fire(Monster monster) {
		count++;
		if (count >= speed) {
			
			if (monster.getArmor() <= 0) {
				monster.setHealth(monster.getHealth() - damage);
			}
			else {
				monster.setArmor(monster.getArmor() - damage);
			}
			
			count = 0;
		}
	}

}
