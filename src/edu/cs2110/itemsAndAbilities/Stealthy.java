package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

/**
 * 
 * Stealthy only effects if ghost see the player, need info about ghost class seeing the player.
 *
 */
public class Stealthy implements Item {
	private String name;
	private int count;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCount() {
		return count;
	}

	public Stealthy() {
		this.name = "Stealthy";
		this.count = 0;
	}

	@Override
	public void useItem(Player p) {
		if (p.isStealthyEffect() == true) {
			
		} else {
			p.setStealthyEffect(true);
			this.decreaseCount(1);
		}
	}

	@Override
	public void useItem(Ghosts g) {

	}

	@Override
	public void useItem(Player p, Ghosts g) {
	
	}

	@Override
	public void increaseCount(int count) {
		this.count = this.count + count;

	}

	@Override
	public void decreaseCount(int count) {
		this.count = this.count - count;
		if (this.count < 0) {
			this.count = 0;
		}
	}

}
