package edu.cs2110.itemsAndAbilities;

import android.os.Parcel;
import android.os.Parcelable;
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

	public Bomb() {
		this.name = "Bomb";
		this.count = 0;
	}
	

	@Override
	public void useItem(Ghosts g) {

	}

	@Override
	public void useItem(Player p) {

	}

	@Override
	public void useItem(Player p, Ghosts g) {
		g.damaged(p, 3);
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
