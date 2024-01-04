package observer;

import java.util.ArrayList;

import model.Tile;

public abstract class Publisher {
	
	protected ArrayList<Subscriber> subscribers;

	public Publisher() {
		this.subscribers = new ArrayList<Subscriber>();
	}
	
	public void subscribeAll(ArrayList<Subscriber> newSubscribers) {
		subscribers.addAll(newSubscribers);
	}
	
	public void subscribe(Subscriber subscriber) {
		subscribers.add(subscriber);
	}
	
	public void unsubscribe(Subscriber subscriber) {
		subscribers.remove(subscriber);
	}
	
	public ArrayList<Subscriber> getSubscribers() {
		return subscribers;
	}
	
	public abstract void notifySubscribers(Tile fromTile);

}
