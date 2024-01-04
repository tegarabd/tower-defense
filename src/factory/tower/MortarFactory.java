package factory.tower;

import factory.TowerFactory;
import model.Tower;
import model.tower.Mortar;

public class MortarFactory extends TowerFactory {

	@Override
	public Tower create() {
		return new Mortar('M', "Mortar", 20, 14, 12, 200);
	}

}
