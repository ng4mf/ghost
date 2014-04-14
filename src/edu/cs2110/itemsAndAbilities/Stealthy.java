package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;
import edu.cs2110.ghost.*;

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
	public boolean useItem(Player p) {
		boolean retVal = false;
		if (p.isStealthyEffect() == true) {
			
		} else {
			p.setStealthyEffect(true);
			retVal = true;
		}
		return retVal;
	}

	@Override
	public boolean useItem(Ghosts g) {
		return false;

	}

	@Override
	public boolean useItem(Player p, Ghosts g) {
		return false;

	}

	@Override
	public boolean increaseCount(int count) {
		this.count = this.count + count;
		return true;
	}

	@Override
	public boolean decreaseCount(int count) {
		this.count = this.count - count;
		if (this.count < 0) {
			this.count = 0;
		}
		return true;
	}

}
