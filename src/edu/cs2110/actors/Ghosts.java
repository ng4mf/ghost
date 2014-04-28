package edu.cs2110.actors;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.Location;
import android.util.Log;

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
	private double speed;
	private double[] direction;
	
	private LatLng cords;
	private Location loc;
	
	private int health;
	private int power;
	private double attackDistance;
	
	public Ghosts() {
		hitbox = new Rect(0,0,0,0);
		frameTimer = 0;
		currentFrame = 0;
		
		//Randomized starting position
		xCoord = 100 + Math.random()*2 - 1;
		yCoord = 100 + Math.random()*2 - 1;

		loc = new Location("Ghost");
		
		health = 10;
		power = 3;
		attackDistance = 200; //calculated from using google coordinate system. 1/4 the width of olson hall.
		speed = .0001;
		
		//Some ghosts will move quickly, others will barely move at all.
		direction = new double[2]; 
		direction[0] = Math.random()*2-1;
		direction[1] = Math.random()*2-1;
	}
	
	public Ghosts(double x, double y) {
		hitbox = new Rect(0,0,0,0);
		frameTimer = 0;
		currentFrame = 0;
		
		health = 10;
		power = 3;
		attackDistance = 200;
		speed = .0001;
		
		//Some ghosts will move quickly, others will barely move at all.
		direction = new double[2]; 
		direction[0] = Math.random()*2-1;
		direction[1] = Math.random()*2-1;
		
		health = 10;
		xCoord = x;
		yCoord = y;
		cords = new LatLng(xCoord, yCoord);
		loc = new Location("Ghost");
		loc.setLatitude(x);
		loc.setLongitude(y);
		
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
		setXCoord(x);
		setYCoord(y);
		cords = new LatLng(x,y);
		//Log.d("Ghosts:", "Error about to occur?");
		loc.setLatitude(x);
		loc.setLongitude(y);
		//Log.d("Ghosts:", "Error did not occur");
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public double[] getDirection(){
		return direction;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		if (health < 0) {
			health = 0;
		}
		this.health = health;
	}
	
	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
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
	
	public void setXCoord(double value) {
		xCoord = value;
		loc.setLatitude(value);
	}
	
	public void setYCoord(double value) {
		yCoord = value;
		loc.setLongitude(value);
	}
	
	public Location getLocation() {
		return loc;
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
	
	/**
	 * Returns true if the ghost will see the player
	 */
	public boolean seePlayer(Player p) {
		boolean retVal = false;
		if (p.isStealthyEffect() == false) {
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * used to check if ghost can attack player
	 */
	public boolean inRange(Player p) {
		boolean retVal = false;
		if (this.loc.distanceTo(p.getLocation()) < attackDistance) {
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Returns true if the ghost attacks the player successfully
	 */
	public boolean attackPlayer(Player p) {
		boolean retVal = false;
		boolean reach = this.inRange(p);
		if (p.isInvincibilityEffect() == false && reach == true) {
			p.damaged(this, 1);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Method accounting for when player damages a ghost
	 */
	public void damaged(Player p, int powerModifier) {
		int gHealth = this.getHealth() - (p.getPower() * powerModifier);
		this.setHealth(gHealth);
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
