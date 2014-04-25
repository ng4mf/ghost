package edu.cs2110.ghost;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import edu.cs2110.actors.Ghosts;

public class GhostThread extends AsyncTask<Void, ArrayList<Ghosts>, Void>{

	private ArrayList<Ghosts> ghosts;
	private boolean run = false;
	private boolean cancel = false;
	private static final String TAG = "GhostThread";
	private int time = 0;
	
	private GameActivity master = null;
	
	public GhostThread() {
		ghosts = new ArrayList<Ghosts>();
		setRunning(true);
	}
	
	public GhostThread(GameActivity g){
		Log.d("GhostThread", "Initiating GhostThread");
		master = g;
		ghosts = new ArrayList<Ghosts>();
		setRunning(true);
		Log.d("GhostThread", "GhostThread Initiated");
	}
	 
	public void setRunning(boolean value) {
		run = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Void... params) {
		//
		ghosts.add(new Ghosts(38.036550, -78.507310));
		while (true) {
			if (run) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				time+=1;
				addGhost();
				for (Ghosts g: ghosts) {
					determineMovement(g);
				}
				
				Log.d(TAG + "bg", "" + ghosts.size());
				publishProgress(ghosts);
			}
			if (cancel){
				break;
			}
		}
		return null;
	}
	
	public void addGhost() {
		Log.d(TAG, "Dif: " + (System.currentTimeMillis()-time));
		if (time > 5) {
			time = 0;
			ghosts.add(new Ghosts(38.036550, -78.507310));
			Log.d(TAG, "Added Ghost");
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
		//Log.d("GhostThread", "onProgressUpdate");
		for (ArrayList<Ghosts> list: g){
			for (Ghosts ghost: list){
				Log.d("GhostThread", ghost.toString());
			}
		}
		Log.d(TAG, "" + g[0].size());
		master.updateScreen(g[0]);
	}

}