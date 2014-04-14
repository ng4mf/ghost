package edu.cs2110.ghost;

import edu.cs2110.actors.Images;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GhostThread extends Thread{

	private SurfaceHolder surfaceHolder;
	private Images panel;
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
	public void run() {
	 
		Canvas c;
		while (run) {
			c = null;
			timer = System.currentTimeMillis();
			panel.onUpdate(timer);
	 
			try {
				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					panel.onDraw(c);
				}
			} finally {
	// do this in a finally so that if an exception is thrown
	// during the above, we don't leave the Surface in an
	// inconsistent state
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	
}
