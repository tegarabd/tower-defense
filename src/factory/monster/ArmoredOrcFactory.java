package factory.monster;

import factory.MonsterFactory;
import model.Monster;
import model.monster.ArmoredOrc;

public class ArmoredOrcFactory extends MonsterFactory {

	@Override
	public Monster create() {
		return new ArmoredOrc('R', "Armored Orc", 2, 400, 600, 9);
	}

}
