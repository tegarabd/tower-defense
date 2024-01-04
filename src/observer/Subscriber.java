package observer;

import model.Monster;

public interface Subscriber {
	void fire(Monster monster);
}
