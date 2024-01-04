package factory.monster;

import factory.MonsterFactory;
import model.Monster;
import model.monster.Goblin;

public class GoblinFactory extends MonsterFactory {

	@Override
	public Monster create() {
		return new Goblin('G', "Goblin", 1, 100, 0, 4);
	}

}
