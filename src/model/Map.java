package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

	private static final Tile TOWER_COORDINATE = new Tile(13, 7);
	private static final Tile MONSTER_START_COORDINATE = new Tile(13, 88);
	private char[][] map;
	private HashMap<String, Tile> towerPlaces;
	private HashMap<Tile, Tower> towers;
	private HashMap<Tile, Monster> monsters;

	public Map() {
		super();
		this.towerPlaces = new HashMap<String, Tile>();
		this.towers = new HashMap<Tile, Tower>();
		this.monsters = new HashMap<Tile, Monster>();
		this.redraw();
	}

	public void redraw() {
		this.towerPlaces.clear();
		this.towers.clear();
		this.monsters.clear();

		this.towerPlaces.put("A", new Tile(8, 18));
		this.towerPlaces.put("B", new Tile(8, 30));
		this.towerPlaces.put("C", new Tile(8, 42));
		this.towerPlaces.put("D", new Tile(8, 54));
		this.towerPlaces.put("E", new Tile(8, 66));
		this.towerPlaces.put("F", new Tile(8, 78));
		this.towerPlaces.put("G", new Tile(18, 18));
		this.towerPlaces.put("H", new Tile(18, 30));
		this.towerPlaces.put("I", new Tile(18, 42));
		this.towerPlaces.put("J", new Tile(18, 54));
		this.towerPlaces.put("K", new Tile(18, 66));
		this.towerPlaces.put("L", new Tile(18, 78));

		this.map = new char[][] { "   /\\".toCharArray(), "   ||".toCharArray(), "  ====".toCharArray(),
				"  |  |".toCharArray(), "  |  |".toCharArray(), "  ====".toCharArray(),
				"  XXXX        =========   =========   =========   =========   =========   =========".toCharArray(),
				"  |\\/|        |       |   |       |   |       |   |       |   |       |   |       |".toCharArray(),
				"  |/\\|        |       |   |       |   |       |   |       |   |       |   |       |".toCharArray(),
				"  |\\/|        |       |   |       |   |       |   |       |   |       |   |       |".toCharArray(),
				"  |/\\|        =========   =========   =========   =========   =========   =========".toCharArray(),
				" /____\\  _________________________________________________________________________________"
						.toCharArray(),
				" | /\\ |                                                                                  "
						.toCharArray(),
				" ||  ||                                                                                  "
						.toCharArray(),
				"/ |__| \\    ___________________________________________________________________________"
						.toCharArray(),
				"|______|".toCharArray(),
				"              =========   =========   =========   =========   =========   =========".toCharArray(),
				"              |       |   |       |   |       |   |       |   |       |   |       |".toCharArray(),
				"              |       |   |       |   |       |   |       |   |       |   |       |".toCharArray(),
				"              |       |   |       |   |       |   |       |   |       |   |       |".toCharArray(),
				"              =========   =========   =========   =========   =========   =========".toCharArray(),
				"".toCharArray(), };

		for (String s : towerPlaces.keySet()) {
			Tile tile = towerPlaces.get(s);
			map[tile.getX() - 2][tile.getY() - 1] = '|';
			map[tile.getX() - 2][tile.getY()] = s.charAt(0);
			map[tile.getX() - 2][tile.getY() + 1] = '|';
		}
	}

	public void print() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	public boolean isMonsterInTowerRange(Tile fromTile, Tower tower) {
		Tile towerTile = towers.keySet().stream().filter(tile -> towers.get(tile).equals(tower)).findFirst().get();

		double d = Math.sqrt(
				Math.pow(towerTile.getX() - fromTile.getX(), 2) + Math.pow(towerTile.getY() - fromTile.getY(), 2));

		return d <= tower.getRange();
	}

	public boolean isTileOccupied(String place) {
		return towers.containsKey(towerPlaces.get(place));
	}

	public void addTower(String place, Tower tower) {
		Tile tile = towerPlaces.get(place);
		map[tile.getX()][tile.getY()] = tower.getCode();
		towers.put(tile, tower);
	}

	public ArrayList<Tower> getTowers() {
		return new ArrayList<Tower>(towers.values());
	}

	public void addMonster(Monster monster) {
		Tile tile = MONSTER_START_COORDINATE.clone();
		map[tile.getX()][tile.getY()] = monster.getCode();
		monsters.put(tile, monster);
	}

	public void updateMonster(Monster monster, Monster newMonster) {
		Tile monsterTile = monsters.keySet().stream().filter(tile -> monsters.get(tile).equals(monster)).findFirst()
				.get();
		monsters.put(monsterTile, newMonster);
	}

	public void performMonstersTick() {
		for (Tile tile : monsters.keySet()) {
			Monster monster = monsters.get(tile);
			monster.performTick();
			if (monster.isTimeToMove()) {
				map[tile.getX()][tile.getY()] = ' ';
				tile.setY(tile.getY() - 1);
				map[tile.getX()][tile.getY()] = monster.getCode();
			}
		}
	}

	public void notifyTowers() {
		for (Tile tile : monsters.keySet()) {
			Monster monster = monsters.get(tile);
			monster.notifySubscribers(tile);
		}
	}

	public boolean isMonsterEnteredTower() {
		for (Tile tile : monsters.keySet()) {
			if (tile.getY() <= TOWER_COORDINATE.getY()) {
				return true;
			}
		}
		return false;
	}

	public boolean isMonsterClear() {
		return monsters.isEmpty();
	}

	public ArrayList<Monster> getDiedMonsters() {
		ArrayList<Monster> diedMonsters = new ArrayList<Monster>();
		for (Monster monster : monsters.values()) {
			if (monster.getHealth() <= 0 && monster.getArmor() <= 0) {
				diedMonsters.add(monster);
			}
		}
		return diedMonsters;
	}

	public void removeDiedMonsters() {
		ArrayList<Tile> toRemoveTiles = new ArrayList<Tile>();

		for (Tile tile : monsters.keySet()) {
			Monster monster = monsters.get(tile);
			if (monster.getHealth() <= 0 && monster.getArmor() <= 0) {
				map[tile.getX()][tile.getY()] = ' ';
				toRemoveTiles.add(tile);
			}
		}

		for (Tile tile : toRemoveTiles) {
			monsters.remove(tile);
		}
	}

	public Tower removeTower(String place) {
		Tile tile = towerPlaces.get(place);
		return towers.remove(tile);
	}

	public boolean isPlaceAvailableForNewMonster() {
		Tile tile = MONSTER_START_COORDINATE;
		return map[tile.getX()][tile.getY()] == ' ';
	}

}
