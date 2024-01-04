package decorator;

import model.Monster;

public class FrostedMonsterDecorator extends MonsterDecorator {

	public FrostedMonsterDecorator(Monster monster) {
		super(monster);
		setSpeed(getSpeed() + 2);
	}

}
