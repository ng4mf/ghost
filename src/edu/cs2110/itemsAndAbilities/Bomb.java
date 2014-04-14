package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

public class Bomb implements Item {
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

	public Bomb(){
		this.name = "Bomb";
		this.count = 0;
	}

	@Override
	public boolean useItem(Ghosts g) {
		boolean retVal = false;
		// damage to ghost
		// need info about ghealth of ghost
		// will be a multiplication of base power
		return retVal;
	}

	@Override
	public boolean useItem(Player p) {
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
