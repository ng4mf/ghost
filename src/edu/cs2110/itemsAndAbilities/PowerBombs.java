package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

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
	public void useItem(Player p) {

	}

	@Override
	public void useItem(Ghosts g) {
		
	}

	@Override
	public void useItem(Player p, Ghosts g) {
		// if power bomb is too close to player, intended to hurt player as well
		boolean willPlayerGetHurt = p.inPowerBombRange(g);
		if (willPlayerGetHurt == true && p.getHealth() > 0
				&& p.isInvincibilityEffect() == false) {
			p.damaged(p, 5);
		}
		g.damaged(p, 5);
		this.decreaseCount(1);
		if (g.getHealth() == 0) {
			p.lootChance();
		}
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
