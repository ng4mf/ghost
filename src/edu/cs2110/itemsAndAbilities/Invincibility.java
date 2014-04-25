package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

public class Invincibility implements Item{
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

	public Invincibility() {
		this.name = "Invincibility";
		this.count = 0;
	}

	@Override
	public void useItem(Player p) {
		if (p.isInvincibilityEffect() == true) {
			
		} else {
		p.setInvincibilityEffect(true);
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
