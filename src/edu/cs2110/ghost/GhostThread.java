package edu.cs2110.ghost;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.view.SurfaceHolder;
import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Images;

public class GhostThread extends AsyncTask<Void, ArrayList<Ghosts>, Void>{

	private SurfaceHolder surfaceHolder;
	private Images panel;
	private ArrayList<Ghosts> ghosts;
	private boolean run = false;
	private long timer;
	private boolean cancel = false;
	
	private GameActivity master = null;
	
	public GhostThread(SurfaceHolder surfaceHolder, Images panel) {
		this.surfaceHolder = surfaceHolder;
		this.panel = panel;
		panel.onInitalize();
	}
	
	private GhostThread() {
		ghosts = new ArrayList<Ghosts>();
		setRunning(true);
		//doInBackground();
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

	@Override
	protected Void doInBackground(Void... params) {
		Canvas c;
		timer = System.currentTimeMillis();
		ghosts.add(new Ghosts(38.036550, -78.507310));
		while (run) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Ghosts g: ghosts) {
				determineMovement(g);
			}
			publishProgress(ghosts);
			//publishProgress();
			/*
			c = null;
			panel.onUpdate(timer);
			try {
				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					//panel.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			*/
			if (cancel){
				break;
			}
		}
		return null;
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
		Log.d("GhostThread", "onProgressUpdate");
		for (ArrayList<Ghosts> list: g){
			for (Ghosts ghost: list){
				Log.d("GhostThread", ghost.toString());
			}
		}
		master.updateScreen(g[0]);
	}

}