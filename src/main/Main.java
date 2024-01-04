package main;

import game.Game;

public class Main {

	private Game game;

	public Main() {
		game = Game.getInstance();
		try {game.run();} catch (InterruptedException e) {}
	}

	public static void main(String[] args) {
		new Main();
	}

}
