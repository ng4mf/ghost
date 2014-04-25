package edu.cs2110.actors;

import java.util.Random;
import edu.cs2110.itemsAndAbilities.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {

	private String userName;
	private int health;
	private int maxHealth;
	private double currency;
	private int power;
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
		if (currency < 0) {
			currency = 0;
		}
		this.currency = currency;

	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
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
	public Player(String name, int maxHealth, int power, double currency,
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
	 * 4 methods Taken from ghost class, not sure if it will be utilized
	 * correctly here. May need to be edited or removed altogether
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
		if (gameTime > frameTimer + fps) {
			frameTimer = gameTime;
			currentFrame += 1;

			if (currentFrame >= numFrames) {
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
	 * Valid Item Names. "Bomb" "Power Bomb" "+Health" "Invincibility"
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
			this.bomb.useItem(this, g);
			retVal = true;
		}
		return retVal;
	}

	/**
	 * Returns true if powerbombs are used
	 */
	public boolean usePowerBomb(Ghosts g) {
		boolean retVal = false;
		boolean reach = this.inRange(g);
		if (this.powerBomb.getCount() >= 1 && reach == true) {
			this.powerBomb.useItem(this, g);
			retVal = true;
		}
		return retVal;
	}

	/**
	 * Returns true if +Health is used
	 */
	public boolean usePlusHealth() {
		boolean retVal = false;
		if (this.plusHealth.getCount() >= 1) {
			this.plusHealth.useItem(this);
			retVal = true;
		}
		return retVal;
	}

	/**
	 * Returns true if Stealthy are used
	 */
	public boolean useStealthy() {
		boolean retVal = false;
		if (this.stealthy.getCount() >= 1) {
			this.stealthy.useItem(this);
			retVal = true;
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
			this.invincibility.useItem(this);
			retVal = true;
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
	 * Valid Item Names. "Bomb" "Power Bomb" "+Health" "Invincibility"
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
		boolean retVal = false;
		boolean reach = this.inRange(g);
		if (reach == true) {
			g.damaged(this, 1);
			if (g.getHealth() == 0) {
				this.lootChance();
			}
		}
		return retVal;
	}

	/**
	 * Method accounting for when ghost damages a player
	 */
	public void damaged(Ghosts g, int powerModifier) {
		int pHealth = this.health - (g.getPower() * powerModifier);
		this.setHealth(pHealth);
	}

	/**
	 * Method accounting for when player damages itself
	 */
	public void damaged(Player p, int powerModifier) {
		int pHealth = this.health - (p.getPower() * powerModifier);
		this.setHealth(pHealth);
	}

	public void gainCurrency(double amount) {
		double current = this.getCurrency();
		current = current + amount;
		this.setCurrency(current);

	}

	public void spendCurrency(double amount) {
		double current = this.getCurrency();
		current = current - amount;
		this.setCurrency(current);
	}

	public void lootChance() {
		Random generator = new Random();
		int g = generator.nextInt(5);
		if (g == 5) {
			this.gainCurrency(600);
			this.addItem("Power Bomb", 1);
		}
		if (g >= 4) {
			this.gainCurrency(100);
			this.addItem("Bomb", 2);
		}
		if (g >= 3) {
			this.gainCurrency(100);
			this.addItem("Bomb", 1);
		}
		if (g >= 2) {
			this.gainCurrency(100);
		}
		if (g >= 1) {
			this.gainCurrency(100);
		}

	}
	/*
	 * public static void main(String[] args) { Player one = new
	 * Player("Player A", 10, 1, 100, 10); Ghosts g = new Ghosts();
	 * one.addItem("Power Bomb", 10); System.out.println(one.getHealth());
	 * one.usePowerBomb(g); System.out.println(one.getHealth()); }
	 */

}
