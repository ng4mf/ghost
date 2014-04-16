package edu.cs2110.actors;

import java.util.Map;
import java.util.TreeMap;

/**
 * Store has the ability to show items for sale
 * Does not contain items, transaction done in Player class
 */
public class Store {
	private Map<String, Integer> priceMap = new TreeMap<String, Integer>();
	
	/**
	 * Should have all items needed, various stores should be able to disable/enable
	 * items, but that functionality should come later
	 */
	public Store() {
		priceMap.put("Bomb", 10);
		priceMap.put("Invincibility", 20);
		priceMap.put("Health", 5);
		priceMap.put("Power Bomb", 25);
		priceMap.put("Stealth Potion", 20);
	}
	
	/**
	 * mainly for easy display of list
	 * @return
	 */
	public Map<String, Integer> getPriceMap() {
		return priceMap;
	}
}
