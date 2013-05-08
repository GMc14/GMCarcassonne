package be.fomp.carcassonne.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import be.fomp.carcassonne.game.objects.FollowerType;
import be.fomp.carcassonne.model.Player;

/**
 * Contains the main rules of the game
 * 
 * @author GMc14
 * 
 */
public class InnsCathedralsRuleset extends Ruleset {
	
	public InnsCathedralsRuleset() {
		STARTING_HAND.put(FollowerType.LARGE_FOLLOWER, new Integer(1));
		MAX_PLAYERS = 6;
		DECK_PATH = "/be/fomp/carcassonne/data/minideck.xml";
	}
	
	
	@Override
	public String toString() {
		return "Inns & Cathedrals";
	}
}
