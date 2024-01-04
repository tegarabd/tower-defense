package factory.monster;

import factory.MonsterFactory;
import model.Monster;
import model.monster.Troll;

public class TrollFactory extends MonsterFactory {

	@Override
	public Monster create() {
		return new Troll('T', "Troll", 2, 800, 0, 7);
	}

}
