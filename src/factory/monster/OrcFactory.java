package factory.monster;

import factory.MonsterFactory;
import model.Monster;
import model.monster.Orc;

public class OrcFactory extends MonsterFactory {

	@Override
	public Monster create() {
		return new Orc('O', "Orc", 2, 300, 0, 3);
	}

}
