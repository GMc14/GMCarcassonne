package be.fomp.carcassonne.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import be.fomp.carcassonne.game.objects.FollowerType;
import be.fomp.carcassonne.model.Player;
import be.fomp.gmc.game.objects.GameObject;

/**
 * Contains the main rules of the game
 * 
 * @author sven
 * 
 */
public class Ruleset {
	// TODO create rulesets for other editions:
	// http://jhywu.tripod.com/CarcRuleV2.htm

	public int MIN_PLAYERS = 2;
	public int MAX_PLAYERS = 5;

	public static final int MAX_TILES_PER_ROW = 35;
	public static final int MAX_TILES_PER_COL = 35;

	public String DECK_PATH = "/be/fomp/carcassonne/data/deck2.xml";
	
	public static final Map<FollowerType, Integer> STARTING_HAND = new HashMap<FollowerType, Integer>() {
        {
            put(FollowerType.FOLLOWER, new Integer(7));
        };
    };


	public static final Comparator<Player> PLAYER_ORDER = new Comparator<Player>() {
		@Override
		public int compare(Player o1, Player o2) {
			return o2.getAge() - o1.getAge();
		}
	};

	public static final int getCityScore(int tiles, int pennants) {
		return (tiles == 2) ? tiles + pennants : 2 * (tiles + pennants);
	}

	public static final int getEndCityScore(int tiles, int pennants) {
		return tiles + pennants;
	}

	public static final int getCloisterScore() {
		return 9;
	}

	public static final int getEndCloisterScore(int surroundingTiles) {
		return surroundingTiles + 1;
	}

	public static final int getRoadScore(int numTiles) {
		return 1 * numTiles;
	}

	public static final int getEndRoadScore(int numTiles) {
		return 1 * numTiles;
	}

	public static final int getEndFarmerScore(int completeCities) {
		return 4 * completeCities;
	}

	public Ruleset() {
	}
	
	
	@Override
	public String toString() {
		return "Original Carcassonne";
	}
}
