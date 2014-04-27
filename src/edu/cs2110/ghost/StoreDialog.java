package edu.cs2110.ghost;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.cs2110.actors.Player;

public class StoreDialog extends DialogFragment implements DialogInterface.OnDismissListener {
	
	private static StoreDialog store;
	
	private TextView mMoney;
	
	private Button mBuyBomb;
	private Button mBuyPowerBomb;
	private Button mBuyInvincibility;
	private Button mBuyHealth;
	private Button mBuyStealth;
	private Button mExitStore;
	
	private static Player player;
	private static GhostThread thread;
	
	private int bombCost;
	private int powerBombCost;
	private int invincibilityCost;
	private int stealthCost;
	private int healthCost;
	
	private static final int REQUEST_DATE = 0;
	
	public StoreDialog() {
		
	}
	
	public static StoreDialog newInstance(GhostThread t, Player p) {
		store = new StoreDialog();
		thread = t;
		thread.setRunning(false);
		player = p;
		
		return store;
	}
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.store, container);
	        
	        this.bombCost = 10;
			this.powerBombCost = 20;
			this.invincibilityCost = 15;
			this.healthCost = 15;
			this.stealthCost = 25;
			
			mMoney = (TextView)v.findViewById(R.id.money_count);
			mMoney.setText("Wallet Contains: $" + player.getCurrency());
			
	        mBuyBomb = (Button)v.findViewById(R.id.buy_bomb);
	        mBuyBomb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					player.addItem("Bomb", 1);
					mMoney.setText("Wallet Contains: $" + player.getCurrency());
				}
	        });

			if (player.getCurrency() < bombCost)
				mBuyBomb.setEnabled(false);
	        
	        
	        mBuyPowerBomb = (Button)v.findViewById(R.id.buy_power_bomb);
	        mBuyPowerBomb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					player.addItem("Power Bomb", 1);
					mMoney.setText("Wallet Contains: $" + player.getCurrency());
				}
	        });
			if (player.getCurrency() < powerBombCost)
				mBuyPowerBomb.setEnabled(false);
	        
	        mBuyInvincibility = (Button)v.findViewById(R.id.buy_invincibility);
	        mBuyInvincibility.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					player.addItem("Invincibility", 1);
					mMoney.setText("Wallet Contains: $" + player.getCurrency());
				}
	        });
			if (player.getCurrency() < invincibilityCost)
				mBuyInvincibility.setEnabled(false);
	        
	        mBuyHealth = (Button)v.findViewById(R.id.buy_health);
	        mBuyHealth.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					player.addItem("+Health", 1);
					mMoney.setText("Wallet Contains: $" + player.getCurrency());
				}
	        });
			if (player.getCurrency() < healthCost)
				mBuyHealth.setEnabled(false);
	        
	        mBuyStealth = (Button)v.findViewById(R.id.buy_stealth);
	        mBuyStealth.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					player.addItem("Stealthy", 1);
					mMoney.setText("Wallet Contains: $" + player.getCurrency());
				}
	        });
			if (player.getCurrency() < stealthCost)
				mBuyStealth.setEnabled(false);
	        
	        mExitStore = (Button)v.findViewById(R.id.exit_store);
	        mExitStore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					store.dismiss();
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
