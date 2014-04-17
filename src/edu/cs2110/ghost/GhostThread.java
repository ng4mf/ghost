package edu.cs2110.ghost;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.SurfaceHolder;
import edu.cs2110.actors.Ghosts;
import edu.cs2110.actors.Images;

class GhostThread extends AsyncTask<Void, ArrayList<Ghosts>, Void>{

	private SurfaceHolder surfaceHolder;
	private Images panel;
	private ArrayList<Ghosts> ghosts;
	private boolean run = false;
	private long timer;
	
	public GhostThread(SurfaceHolder surfaceHolder, Images panel) {
		this.surfaceHolder = surfaceHolder;
		this.panel = panel;
		panel.onInitalize();
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
				double x = g.getXCoord();
				double y = g.getYCoord();
				
				x += 1;
				y += 1;
				g.updateCords(x, y);
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
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(ArrayList<Ghosts>...g) {
		//updateScreen(g[0]);
	}
}
