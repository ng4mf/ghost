package edu.cs2110.actors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.google.android.gms.maps.model.LatLng;

public class Ghosts {
	
	private Bitmap animation;
	private double xCoord;
	private double yCoord;
	private Rect hitbox;
	private int fps;
	private int numFrames;
	private int currentFrame;
	private long frameTimer;
	private int spriteHeight;
	private int spriteWidth;
	private LatLng cords;
	
	private int health;
	
	public Ghosts() {
		hitbox = new Rect(0,0,0,0);
		frameTimer = 0;
		currentFrame = 0;
		xCoord = 100;
		yCoord = 100;
		health = 10;
	}
	
	public Ghosts(double x, double y) {
		health = 10;
		xCoord = x;
		yCoord = y;
		cords = new LatLng(xCoord, yCoord);
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
	
	public void updateCords(double x, double y) {
		xCoord = x;
		yCoord = y;
		cords = new LatLng(x,y);
	}
	
	public void setCoordinates(LatLng c) {
		cords = c;
	}
	
	public double getXCoord() {
		return xCoord;
	}
	
	public double getYCoord() {
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
	
	@Override
	public String toString() {
		return "Ghost at " + xCoord + ", " + yCoord;
	}
	/*
	public void draw(Canvas canvas) {
		Rect dest = new Rect(getXCoord(), getYCoord(), getXCoord() + spriteWidth,
				getYCoord() + spriteHeight);
		canvas.drawBitmap(animation, hitbox, dest, null);
	}
	*/

}
