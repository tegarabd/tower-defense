package factory.tower;

import factory.TowerFactory;
import model.Tower;
import model.tower.TeslaCoil;

public class TeslaCoilFactory extends TowerFactory {

	@Override
	public Tower create() {
		return new TeslaCoil('T', "Tesla Coil", 10, 6, 4, 200);
	}

}
