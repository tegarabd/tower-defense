package game;

import java.util.ArrayList;
import java.util.Random;

import factory.MonsterFactory;
import factory.TowerFactory;
import factory.monster.ArmoredGoblinFactory;
import factory.monster.ArmoredOrcFactory;
import factory.monster.GoblinFactory;
import factory.monster.OrcFactory;
import factory.monster.TrollFactory;
import factory.tower.BallistaFactory;
import factory.tower.FlameThrowerFactory;
import factory.tower.FrostKeepFactory;
import factory.tower.MortarFactory;
import factory.tower.TeslaCoilFactory;
import input.Input;
import model.Map;
import model.Monster;
import model.Tile;
import model.Tower;

public class Game {

	private static final int MAX_MONSTER_COUNT = 30;

	private static Game instance;

	private int gold;
	private int level;
	private int count;
	private Map map;
	private Input input;
	private Random random;
	private ArrayList<TowerFactory> towerFactories;
	private ArrayList<MonsterFactory> monsterFactories;

	private Game() {
		gold = 100;
		level = 1;
		map = new Map();
		input = new Input();
		random = new Random();
		towerFactories = new ArrayList<TowerFactory>();
		monsterFactories = new ArrayList<MonsterFactory>();
		towerFactories.add(new BallistaFactory());
		towerFactories.add(new MortarFactory());
		towerFactories.add(new TeslaCoilFactory());
		towerFactories.add(new FrostKeepFactory());
		towerFactories.add(new FlameThrowerFactory());
		monsterFactories.add(new GoblinFactory());
		monsterFactories.add(new OrcFactory());
		monsterFactories.add(new ArmoredGoblinFactory());
		monsterFactories.add(new TrollFactory());
		monsterFactories.add(new ArmoredOrcFactory());
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}

		return instance;
	}

	private void printStat() {
		System.out.println("===================");
		System.out.println("Gold  : " + gold);
		System.out.println("Level : " + level);
		System.out.println("===================");
	}

	public void run() throws InterruptedException {

		int choice = -1;

		while (true) {
			input.clear();
			map.print();
			printStat();

			System.out.println();
			System.out.println("1. Buy Tower");
			System.out.println("2. Sell Tower");
			System.out.println("3. Start Level");
			System.out.println("4. Exit");

			choice = input.validateNextInt(n -> n >= 1 && n <= 4);

			switch (choice) {
			case 1:
				buyTower();
				break;
			case 2:
				sellTower();
				break;
			case 3:
				startLevel();
				break;
			case 4:
				System.exit(0);
			}

		}
	}

	private void sellTower() {
		input.clear();
		map.print();
		printStat();

		System.out.println();
		String place = input.validateNextLine(s -> "ABCDEFGHIJKL".contains(s), "Choose tower place to sell [A-L]");

		Tower toRemoveTower = map.removeTower(place);
		int usedTowerPrice = getUsedTowerPrice(toRemoveTower);
		gold += usedTowerPrice;

		System.out.println();
		System.out.println("You got " + usedTowerPrice + " for selling " + toRemoveTower.getName());

		input.enter();
	}

	public void buyTower() {

		input.clear();
		map.print();
		printStat();
		printTowers();

		int towerIndex = input.validateNextInt(n -> n >= 1 && n <= 5, "Choose tower [1-5]");
		String place = input.validateNextLine(s -> "ABCDEFGHIJKL".contains(s), "Choose place for tower [A-L]");
		Tower tower = towerFactories.get(towerIndex - 1).create();

		if (gold < tower.getPrice()) {
			System.out.println();
			System.out.println("Not enough gold");
			input.enter();
			return;
		}

		System.out.println();

		if (map.isTileOccupied(place)) {
			System.out.println();
			System.out.println("Place already occupied by a tower");

			String confirm = input.validateNextLine(s -> s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("N"),
					"Do you want to replace it with the new one? [Y/n]");

			if (confirm.equalsIgnoreCase("N")) {
				return;
			}

			Tower toRemoveTower = map.removeTower(place);
			int usedTowerPrice = getUsedTowerPrice(toRemoveTower);
			gold += usedTowerPrice;

			System.out.println("You got " + usedTowerPrice + " gold for selling " + toRemoveTower.getName());
		}

		map.addTower(place, tower);
		gold -= tower.getPrice();

		System.out.println("Successfully placed " + tower.getName() + " at place " + place);
		input.enter();

	}

	private int getUsedTowerPrice(Tower tower) {
		return tower.getPrice() * 80 / 100;
	}

	private void printTowers() {
		int i = 1;
		System.out.println();
		for (TowerFactory towerFactory : towerFactories) {
			Tower tower = towerFactory.create();
			System.out.print(i++ + ". ");
			tower.print();
			System.out.println();
		}
		System.out.println();
	}

	public void startLevel() throws InterruptedException {
		while (true) {
			Thread.sleep(500);
			map.performMonstersTick();
			map.notifyTowers();
			gold += map.getDiedMonsters().stream().reduce(0, (prev, curr) -> prev + curr.getDrop(), Integer::sum);
			map.removeDiedMonsters();
			input.clear();
			map.print();
			printStat();

			if (map.isMonsterEnteredTower()) {
				System.out.println();
				System.out.println("Monster have entered tower! You lose");
				map.redraw();
				gold = 100;
				level = 1;
				count = 0;
				input.enter();
				return;
			}

			if (count >= MAX_MONSTER_COUNT && map.isMonsterClear()) {
				System.out.println();
				System.out.println("Congratulation! You have escaped level " + level + ". Next level: " + (++level));
				count = 0;
				input.enter();
				return;
			}

			if (random.nextInt(4) <= level && count < MAX_MONSTER_COUNT && map.isPlaceAvailableForNewMonster()) {
				addRandomMonster();
			}
		}
	}

	private int getMaxMonsterLevel() {
		if (level >= 10) {
			return 5;
		}

		return (int) Math.ceil((double) level / 2);
	}

	private void addRandomMonster() {

		int maxMonsterLevel = getMaxMonsterLevel();
		int index = random.nextInt(maxMonsterLevel);

		if (index + 1 == maxMonsterLevel) {
			count++;
		}

		Monster monster = monsterFactories.get(index).create();

		for (Tower tower : map.getTowers()) {
			monster.subscribe(tower);
		}

		map.addMonster(monster);
	}

	public boolean isMonsterInTowerRange(Tile fromTile, Tower tower) {
		return map.isMonsterInTowerRange(fromTile, tower);
	}

	public void updateMonster(Monster monster, Monster newMonster) {
		map.updateMonster(monster, newMonster);
	}
}
