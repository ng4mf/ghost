package edu.cs2110.ghost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;

import android.os.AsyncTask;
import android.util.Log;
import edu.cs2110.actors.Ghosts;

public class GhostThread extends AsyncTask<Void, ArrayList<Ghosts>, Void>{

	private Collection<Ghosts> ghosts;
	private boolean run = false;
	private boolean cancel = false;
	private static final String TAG = "GhostThread";
	private int time = 0;
	
	private boolean removed = false;
	
	private GameActivity master = null;
	
	public GhostThread() {
		ghosts = new ArrayList<Ghosts>();
		setRunning(true);
	}

	public GhostThread(GameActivity g){
		//Log.d("GhostThread", "Initiating GhostThread");
		Log.d(TAG, "Started making thread");
		master = g;
		//ghosts = Collections.synchronizedCollection(master.getGhosts());
		setRunning(true);
		//Log.d("GhostThread", "GhostThread Initiated");
	}
	 
	public void setRunning(boolean value) {
		run = value;
	}
	
	public void setRemoved(boolean v) {
		removed = v;
	}
	
	public void setCanceled(boolean value) {
		cancel = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected synchronized Void doInBackground(Void... params) {
		//
		//ghosts = master.getGhosts();
		Log.d(TAG, "Started executing");
		cancel = false;
		while (true) {
			if (run) {
				//ghosts = master.getGhosts();
				//Log.d(TAG, "Did not die yet");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//for (Ghosts g: master.getGhosts()) {
				//	Log.d(TAG, "Changing ArrayList");
				try {
				master.changeGhosts("update", null);
				//}
				time += 1;
				addGhost();
				ghostAttack();
//				Log.d("Thread", "Ghost in list");
				
				//ghosts = master.getGhosts();
				publishProgress((ArrayList<Ghosts>)(master.getGhosts()));
				} catch (ConcurrentModificationException c) {
					Log.d(TAG, c.getMessage().toString());
					continue;
				}
			}
			if (cancel){
				break;
			}
		}
		return null;
	}
	
	public void ghostAttack() {
		master.changeGhosts("attack", null);
	}
	
	public void addGhost() {
		//Log.d(TAG, "Dif: " + (System.currentTimeMillis()-time));
		if (time > 5) {
			time = 0;
//			Log.d(TAG, "Almost added");
			master.changeGhosts("add", new Ghosts(38.036550, -78.507310));
			//ghosts.add(new Ghosts(38.036550, -78.507310));
			//Log.d(TAG, "Added Ghost");
		}
	}
	
	public void determineMovement(Ghosts g){
		double x = g.getXCoord();
		double y = g.getYCoord();
		x += g.getSpeed()*g.getDirection()[0];
		y += g.getSpeed()*g.getDirection()[1];
		//Toast.makeText(master.getApplicationContext(), "Speed =" + g.getSpeed(), Toast.LENGTH_SHORT).show();
		g.updateCords(x, y);//to be removed
		
		/*
 		// Ghosts wander until they get close enough to see the player
 		
 		double pX = master.getPlayer().getXCoord();
		double pY = master.getPlayer().getYCoord();
		
		double dX = 0;
		double dY = 0;
		
		if(Math.sqrt(Math.pow(pX - x, 2) + Math.pow(pY - y, 2)) > 1 /*This distance is arbitrary*\/){
			// They are far apart, so the ghost wanders
			dX = g.getSpeed()*g.getDirection()[0];
			dY = g.getSpeed()*g.getDirection()[1];

		}else{
			//They are close, so the ghost tracks the player
			// The ghost should also speed up significantly
			dX = g.getSpeed()*Math.signum(pX - x);
			dY = g.getSpeed()*Math.signum(pY - y);
		}


		
		g.updateCords(x+dX, y+dY);		
		*/
	}
	
	@Override
	protected void onProgressUpdate(ArrayList<Ghosts>...g) {
		/*for (ArrayList<Ghosts> list: g){
			for (Ghosts ghost: list){
				//Log.d("GhostThread", ghost.toString());
			}
		}*/
		//master.updateScreen(g[0]);
		master.changeGhosts("updateAll", null);
	}

}