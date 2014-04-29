package edu.cs2110.ghost;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.cs2110.actors.Player;

public class InventoryDialog extends DialogFragment {

	private static InventoryDialog inventory;
	
	private TextView money;
	private TextView bombs;
	private TextView powerBombs;
	private TextView invincibilityPotions;
	private TextView healthPotions;
	private TextView stealthPotions;
	
	private Button setBomb;
	private Button setPowerBomb;
	private Button useInvincibility;
	private Button useHealth;
	private Button useStealth;
	private Button exitButton;
	
	private Player player;
	private GhostThread thread;
	
	public InventoryDialog() {
		
	}
	
	public static InventoryDialog newInstance(Player player, GhostThread t) {
		inventory = new InventoryDialog();
		inventory.setPlayer(player);
		inventory.setThread(t);
		return inventory;
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public void setThread(GhostThread t) {
		thread = t;
	}
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        
			getDialog().setTitle("Inventory");
			
			View v = inflater.inflate(R.layout.inventory, container);
	        
	        money = (TextView)v.findViewById(R.id.money_display);
	        money.setText("Wallet contains: " + player.getCurrency());
	        
	        bombs = (TextView)v.findViewById(R.id.bomb_count);
	        bombs.setText("" + player.getBombCount());
	        
	        setBomb = (Button)v.findViewById(R.id.bomb_select);
	        setBomb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					player.setWeapon("bomb");
				}
	        });
	        if (player.getBombCount() <= 0) {
	        	setBomb.setEnabled(false);
	        }
	        else setBomb.setEnabled(true);
	        
	        powerBombs = (TextView)v.findViewById(R.id.power_bomb_count);
	        powerBombs.setText("" + player.getPowerBombCount());
	        
	        setPowerBomb = (Button)v.findViewById(R.id.power_bomb_select);
	        setPowerBomb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					player.setWeapon("power");
				}
	        });
	        if (player.getPowerBombCount() <= 0) {
	        	setPowerBomb.setEnabled(false);
	        }
	        else setPowerBomb.setEnabled(true);
	        
	        invincibilityPotions = (TextView)v.findViewById(R.id.invincibility_count);
	        invincibilityPotions.setText("" + player.getInvincibilityCount());
	        
	        useInvincibility = (Button)v.findViewById(R.id.use_invincibility);
	        useInvincibility.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					player.useInvincibility();
				}
	        });
	        if (player.getInvincibilityCount() <= 0) {
	        	useInvincibility.setEnabled(false);
	        }
	        else useInvincibility.setEnabled(true);
	        
	        healthPotions = (TextView)v.findViewById(R.id.health_potion_count);
	        healthPotions.setText("" + player.getHealthPotionCount());
	        
	        useHealth = (Button)v.findViewById(R.id.use_health_potion);
	        useHealth.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					player.usePlusHealth();
				}
	        });
	        if (player.getHealthPotionCount() <= 0) {
	        	useHealth.setEnabled(false);
	        }
	        else useHealth.setEnabled(true);
	        
	        stealthPotions = (TextView)v.findViewById(R.id.stealth_count);
	        stealthPotions.setText("" + player.getStealthCount());
	        
	        useStealth = (Button)v.findViewById(R.id.use_stealth);
	        useStealth.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					player.useStealthy();
				}
	        });
	        if (player.getStealthCount() <= 0) {
	        	useStealth.setEnabled(false);
	        }
	        else useStealth.setEnabled(true);
	        
	        exitButton = (Button)v.findViewById(R.id.exit_inventory);
	        exitButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					inventory.dismiss();
				}
	        	
	        });
	        
	        return v;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		thread.setRunning(true);
	}
}
