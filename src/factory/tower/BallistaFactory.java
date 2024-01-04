package factory.tower;

import factory.TowerFactory;
import model.Tower;
import model.tower.Ballista;

public class BallistaFactory extends TowerFactory {

	@Override
	public Tower create() {
		return new Ballista('B', "Ballista", 10, 9, 6, 10);
	}

}
