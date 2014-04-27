package edu.cs2110.itemsAndAbilities;

import android.os.Parcelable;
import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

public interface Item {
	
	public String getName();
	
	public int getCount();

	public void useItem(Player p);

	public void useItem(Ghosts g);

	public void useItem(Player p, Ghosts g);
	
	public void increaseCount(int count);
	
	public void decreaseCount(int count);

	
}