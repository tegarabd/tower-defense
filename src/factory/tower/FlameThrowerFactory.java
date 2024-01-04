package factory.tower;

import factory.TowerFactory;
import model.Tower;
import model.tower.FlameThrower;

public class FlameThrowerFactory extends TowerFactory {

	@Override
	public Tower create() {
		return new FlameThrower('L', "Flame Thrower", 5, 8, 2, 300);
	}

}
