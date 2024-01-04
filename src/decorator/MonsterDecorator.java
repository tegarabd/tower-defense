package decorator;

import model.Monster;

public abstract class MonsterDecorator extends Monster {

	public MonsterDecorator(Monster monster) {
		super(monster.getCode(), monster.getName(), monster.getSpeed(), monster.getHealth(), monster.getArmor(),
				monster.getDrop());
		subscribeAll(monster.getSubscribers());
	}

}
