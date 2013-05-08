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
public class TradersBuildersRuleset extends Ruleset {
	
	public TradersBuildersRuleset() {
		STARTING_HAND.put(FollowerType.PIG, new Integer(1));
		STARTING_HAND.put(FollowerType.BUILDER, new Integer(1));
	}
	public static final String DECK_PATH = "/be/fomp/carcassonne/data/deck2.xml";
	
	@Override
	public String toString() {
		return "Traders & Builders";
	}
}
