package decorator;

import model.Monster;

public class BurnedMonsterDecorator extends MonsterDecorator {

	public BurnedMonsterDecorator(Monster monster) {
		super(monster);
	}
	
	@Override
	public void performTick() {
		super.performTick();
		if (getArmor() <= 0) {
			setHealth(getHealth() - 3);
		}
		else {
			setArmor(getArmor() - 3);
		}
	}

}
