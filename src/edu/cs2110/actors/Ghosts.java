package edu.cs2110.actors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ghosts {
	
	private Bitmap animation;
	private int xCoord;
	private int yCoord;
	private Rect hitbox;
	private int fps;
	private int numFrames;
	private int currentFrame;
	private long frameTimer;
	private int spriteHeight;
	private int spriteWidth;
	
	public Ghosts() {
		hitbox = new Rect(0,0,0,0);
		frameTimer = 0;
		currentFrame = 0;
		xCoord = 100;
		yCoord = 100;
	}
	
	public void Initialize(Bitmap bitmap, int height, int width, int fps, int frameCount) {
		this.animation = bitmap;
		this.spriteHeight = height;
		this.spriteWidth = width;
		this.hitbox.top = 0;
		this.hitbox.bottom = spriteHeight;
		this.hitbox.left = 0;
		this.hitbox.right = spriteWidth;
		this.fps = 1000 / fps;
		this.numFrames = frameCount;
	}
	
	public int getXCoord() {
		return xCoord;
	}
	
	public int getYCoord() {
		return yCoord;
	}
	
	public void setXCoord(int value) {
		xCoord = value;
	}
	
	public void setYCoord(int value) {
		yCoord = value;
	}
	
	public void update(long gameTime) {
		if(gameTime > frameTimer + fps) {
			frameTimer = gameTime;
			currentFrame += 1;
			
			if(currentFrame >= numFrames) {
				currentFrame = 0;
			}
			
			hitbox.left = currentFrame * spriteWidth;
			hitbox.right = hitbox.left + spriteWidth;
		}
	}
	
	public void draw(Canvas canvas) {
		Rect dest = new Rect(getXCoord(), getYCoord(), getXCoord() + spriteWidth,
				getYCoord() + spriteHeight);
		canvas.drawBitmap(animation, hitbox, dest, null);
	}

}
