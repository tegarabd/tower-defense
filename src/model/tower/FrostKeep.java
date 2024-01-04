package model.tower;

import decorator.FrostedMonsterDecorator;
import game.Game;
import model.Monster;
import model.Tower;

public class FrostKeep extends Tower {

	public FrostKeep(char code, String name, int damage, int range, int speed, int price) {
		super(code, name, damage, range, speed, price);
	}

	@Override
	public void fire(Monster monster) {
		Monster newMonster = new FrostedMonsterDecorator(monster);
		Game.getInstance().updateMonster(monster, newMonster);
		super.fire(newMonster);
	}
	
	@Override
	public void print() {
		super.print();
		System.out.print(" + Frost monster (decrease monster speed by 2)");
	}

}
