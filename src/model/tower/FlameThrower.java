package model.tower;

import decorator.BurnedMonsterDecorator;
import game.Game;
import model.Monster;
import model.Tower;

public class FlameThrower extends Tower {

	public FlameThrower(char code, String name, int damage, int range, int speed, int price) {
		super(code, name, damage, range, speed, price);
	}
	
	@Override
	public void fire(Monster monster) {
		Monster newMonster = new BurnedMonsterDecorator(monster);
		Game.getInstance().updateMonster(monster, newMonster);
		super.fire(newMonster);
	}
	
	@Override
	public void print() {
		super.print();
		System.out.print(" + Burn monster (decrease monster health by 3 per tick)");
	}
}
