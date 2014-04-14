package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;
import edu.cs2110.ghost.*;

public class PowerBombs implements Item {
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

	public PowerBombs() {
		this.name = "Power Bomb";
		this.count = 0;
	}

	@Override
	public boolean useItem(Player p) {
		return false;
		
	}

	@Override
	public boolean useItem(Ghosts g) {
		return false;
	}

	@Override
	public boolean useItem(Player p, Ghosts g) {
		// if power bomb is too close to player, intended to hurt player as well
		// need info about ghost class
		boolean retVal = false;
		boolean willPlayerGetHurt = p.inPowerBombRange(g);
		if (willPlayerGetHurt == true && p.getHealth() >= 0 && p.isInvincibilityEffect() == false) {
			int health = p.getHealth() - 3;
			p.setHealth(health);
			retVal = true;
		}
		// need if statement for ghost class health
		return retVal;
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
