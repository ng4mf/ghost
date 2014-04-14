package edu.cs2110.ghost;

import edu.cs2110.actors.Images;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class Drawable extends SurfaceView implements SurfaceHolder.Callback, Images{
	private GhostThread thread;
	 
	public Drawable(Context context) {
		super(context);
		getHolder().addCallback(this);
	 
		this.thread = new GhostThread(getHolder(), this);
	}
	 
	@Override
	public void onDraw(Canvas canvas) {
	}
	 
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	 
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}
	 
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
	// we will try it again and again...
			}
		}          
	}
	
}
