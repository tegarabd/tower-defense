package factory.tower;

import factory.TowerFactory;
import model.Tower;
import model.tower.FrostKeep;

public class FrostKeepFactory extends TowerFactory {

	@Override
	public Tower create() {
		return new FrostKeep('F', "Frost Keep", 5, 6, 1, 250);
	}

}
