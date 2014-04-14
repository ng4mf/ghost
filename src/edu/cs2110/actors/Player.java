package edu.cs2110.actors;

import edu.cs2110.itemsAndAbilities.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {

	private String userName;
	private int health;
	private int maxHealth;
	private double currency;
	private double power;
	private double attackDistance;
	private double xCoord;
	private double yCoord;
	private boolean stealthEffect;
	private boolean invincibilityEffect;

	private Item bomb;
	private Item powerBomb;
	private Item plusHealth;
	private Item invincibility;
	private Item stealthy;
	
	private Bitmap animation;
	private int currentFrame;
	private long frameTimer;
	private int spriteHeight;
	private int spriteWidth;
	private Rect hitbox;
	private int numFrames;
	private int fps;

	/**
	 * Getters and Setters
	 */
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health > this.maxHealth) {
			health = this.maxHealth;
		}
		if (health < 0) {
			health = 0;
		}
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		if (maxHealth < 0) {
			maxHealth = 0;
		}
		this.maxHealth = maxHealth;
		if (this.health > this.maxHealth) {
			this.setHealth(maxHealth);
		}
	}

	public double getCurrency() {
		return currency;
	}

	public void setCurrency(double currency) {
		this.currency = currency;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getAttackRadius() {
		return attackDistance;
	}

	public void setAttackRadius(double radius) {
		this.attackDistance = radius;
	}

	public double getXCoord() {
		return xCoord;
	}

	public void setXCoord(double xCoord) {
		this.xCoord = xCoord;
	}

	public double getYCoord() {
		return yCoord;
	}

	public void setYCoord(double yCoord) {
		this.yCoord = yCoord;
	}

	public boolean isStealthyEffect() {
		return stealthEffect;
	}

	public void setStealthyEffect(boolean stealthy) {
		this.stealthEffect = stealthy;
	}

	public boolean isInvincibilityEffect() {
		return invincibilityEffect;
	}

	public void setInvincibilityEffect(boolean invincibility) {
		this.invincibilityEffect = invincibility;
	}

	/**
	 * Constructors for Player
	 */
	public Player(String name, int maxHealth, double power, double currency,
			double attackDistance) {
		this.userName = name;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.power = power;
		this.currency = currency;
		this.attackDistance = attackDistance;
		this.xCoord = 0;
		this.yCoord = 0;
		this.stealthEffect = false;
		this.invincibilityEffect = false;

		this.bomb = new Bomb();
		this.powerBomb = new PowerBombs();
		this.plusHealth = new PlusHealth();
		this.invincibility = new Invincibility();
		this.stealthy = new Stealthy();
	}

	/**
	 * 4 methods Taken from ghost class, not sure if it will be utilized correctly here.
	 * May need to be edited or removed altogether
	 */
	public void Initialize(Bitmap bitmap, int height, int width, int fps,
			int frameCount) {
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
	
	public void Update(long gameTime) {
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
		Rect dest = new Rect((int) getXCoord(), (int) getYCoord(),
				(int) getXCoord() + spriteWidth, (int) getYCoord()
						+ spriteHeight);
		canvas.drawBitmap(animation, hitbox, dest, null);
	}

	public void updatePosition(double x, double y) {
		this.setXCoord(x);
		this.setYCoord(y);
	}

	/**
	 * used to check if player can attack a ghost
	 */
	public boolean inRange(Ghosts g) {
		boolean retVal = false;
		double ghostPosX = g.getXCoord();
		double ghostPosY = g.getYCoord();
		if (Math.pow((this.xCoord - ghostPosX), 2)
				+ Math.pow((this.yCoord - ghostPosY), 2) <= Math.pow(
				this.attackDistance, 2)) {
			retVal = true;
		}
		return retVal;
	}

	/**
	 * used to check if player will get hurt by powerbomb
	 */
	public boolean inPowerBombRange(Ghosts g) {
		boolean retVal = false;
		double ghostPosX = g.getXCoord();
		double ghostPosY = g.getYCoord();
		double powerBombHurtRange = this.attackDistance / 3;
		if (Math.pow((this.xCoord - ghostPosX), 2)
				+ Math.pow((this.yCoord - ghostPosY), 2) <= Math.pow(
				powerBombHurtRange, 2)) {
			retVal = true;
		}
		return retVal;
	}

	/**
	 * Valid Item Names.
	 * "Bomb"
	 * "Power Bomb"
	 * "+Health"
	 * "Invincibility"
	 * "Stealthy"
	 * 
	 * used to add a quantity to an item
	 */
	public boolean addItem(String itemName, int count) {
		boolean retVal = false;
		if (count < 1) {
			count = 1;
		}
		if (this.bomb.getName().equals(itemName)) {
			retVal = true;
			this.bomb.increaseCount(count);
		} else if (this.powerBomb.getName().equals(itemName)) {
			retVal = true;
			this.powerBomb.increaseCount(count);
		} else if (this.plusHealth.getName().equals(itemName)) {
			retVal = true;
			this.plusHealth.increaseCount(count);
		} else if (this.invincibility.getName().equals(itemName)) {
			retVal = true;
			this.invincibility.increaseCount(count);
		} else if (this.stealthy.getName().equals(itemName)) {
			retVal = true;
			this.stealthy.increaseCount(count);
		}

		return retVal;
	}

	/**
	 * Returns true if bombs are used
	 */
	public boolean useBomb(Ghosts g) {
		boolean retVal = false;
		boolean reach = this.inRange(g);
		if (this.bomb.getCount() >= 1 && reach == true) {
			boolean itemCheck = this.bomb.useItem(g);
			if (itemCheck == true) {
				this.bomb.decreaseCount(1);
				retVal = true;
			}
		}
		return retVal;
	}

	/**
	 * Returns true if powerbombs are used
	 */
	public boolean usePowerBomb(Ghosts g) {
		boolean retVal = false;
		boolean reach = this.inRange(g);
		if (this.powerBomb.getCount() >= 1
				&& reach == true) {
			boolean itemCheck = this.powerBomb.useItem(this, g);
			if (itemCheck == true) {
				this.powerBomb.decreaseCount(1);
				retVal = true;
			}
		}
		return retVal;
	}

	/**
	 * Returns true if +Health is used
	 */
	public boolean usePlusHealth() {
		boolean retVal = false;
			if (this.plusHealth.getCount() >= 1) {
				boolean itemCheck = this.plusHealth.useItem(this);
				if (itemCheck == true) {
					this.plusHealth.decreaseCount(1);
					retVal = true;
				}
			}
		return retVal;
	}

	/**
	 * Returns true if Stealthy are used
	 */
	public boolean useStealthy() {
		boolean retVal = false;
			if (this.stealthy.getCount() >= 1) {
				boolean itemCheck = this.stealthy.useItem(this);
				if (itemCheck == true) {
					this.stealthy.decreaseCount(1);
					retVal = true;
				}
			}
		return retVal;
	}

	/**
	 * used to end stealthy
	 */
	public boolean endStealthy() {
		boolean retVal = false;
		if (this.isStealthyEffect() == true) {
			this.setStealthyEffect(false);
			retVal = true;
		}
		return retVal;
	}

	/**
	 * Returns true if Invincibility are used
	 */
	public boolean useInvincibility() {
		boolean retVal = false;
			if (this.invincibility.getCount() >= 1) {
				boolean itemCheck = this.invincibility.useItem(this);
				if (itemCheck == true) {
					this.invincibility.decreaseCount(1);
					retVal = true;
				}
			}
		return retVal;
	}

	/**
	 * used to end invincibility
	 */
	public boolean endInvincibility() {
		boolean retVal = false;
		if (this.isStealthyEffect() == true) {
			this.setInvincibilityEffect(false);
			retVal = true;
		}
		return retVal;
	}

	/**
	 * Valid Item Names.
	 * "Bomb"
	 * "Power Bomb"
	 * "+Health"
	 * "Invincibility"
	 * "Stealthy"
	 * 
	 * used to remove a quantity from an item
	 */
	public boolean removeItem(String itemName, int count) {
		boolean retVal = false;
		if (this.bomb.getName().equals(itemName)) {
			retVal = true;
			this.bomb.decreaseCount(count);
		} else if (this.powerBomb.getName().equals(itemName)) {
			retVal = true;
			this.powerBomb.decreaseCount(count);
		} else if (this.plusHealth.getName().equals(itemName)) {
			retVal = true;
			this.plusHealth.decreaseCount(count);
		} else if (this.invincibility.getName().equals(itemName)) {
			retVal = true;
			this.invincibility.decreaseCount(count);
		} else if (this.stealthy.getName().equals(itemName)) {
			retVal = true;
			this.stealthy.decreaseCount(count);
		}
		return retVal;
	}

	/**
	 * used to attack ghost without an item
	 */
	public boolean attackGhost(Ghosts g) {
		// need info about ghost class health
		// will damage ghost based on power
		boolean retVal = false;
		boolean reach = this.inRange(g);
		if (reach == true) {

		}
		return retVal;
	}

	/** 
	 * used to account for damage by ghost
	 */
	public boolean hurtByGhost(Ghosts g) {
		boolean retVal = true;
		if (this.invincibilityEffect == false) {
			// need info about ghost damage to player
			// or a method for ghost attacking player needs to be in ghost class while getting rid of this one

		}
		return retVal;
	}
	/*
	public static void main(String[] args) {
		Player one = new Player("Player A", 10, 1, 100, 10);
		Ghosts g = new Ghosts();
		one.addItem("Power Bomb", 10);
		System.out.println(one.getHealth());
		one.usePowerBomb(g);
		System.out.println(one.getHealth());
	}
	*/
	
}


