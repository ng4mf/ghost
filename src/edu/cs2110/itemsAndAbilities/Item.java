package edu.cs2110.itemsAndAbilities;

import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Player;

public interface Item {
	
	public String getName();
	
	public int getCount();

	public boolean useItem(Player p);

	public boolean useItem(Ghosts g);

	public boolean useItem(Player p, Ghosts g);
	
	public boolean increaseCount(int count);
	
	public boolean decreaseCount(int count);

}