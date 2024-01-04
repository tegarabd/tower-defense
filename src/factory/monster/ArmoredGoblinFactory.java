package factory.monster;

import factory.MonsterFactory;
import model.Monster;
import model.monster.ArmoredGoblin;

public class ArmoredGoblinFactory extends MonsterFactory {

	@Override
	public Monster create() {
		return new ArmoredGoblin('A', "Armored Goblin", 2, 400, 200, 5);
	}

}
