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

public class StoreDialog extends DialogFragment implements DialogInterface.OnDismissListener {
	
	private static StoreDialog store;
	
	private Button mBuyBomb;
	private Button mBuyPowerBomb;
	private Button mBuyInvincibility;
	private Button mBuyHealth;
	private Button mBuyStealth;
	private Button mExitStore;
	
	private static GhostThread thread;
	
	private static final int REQUEST_DATE = 0;
	
	public StoreDialog() {
		
	}
	
	public static StoreDialog newInstance(GhostThread t) {
		store = new StoreDialog();
		thread = t;
		thread.setRunning(false);
		return store;
	}
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.store, container);
	        
	        mBuyBomb = (Button)v.findViewById(R.id.buy_bomb);
	        mBuyBomb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
				}
	        });
	        
	        mBuyPowerBomb = (Button)v.findViewById(R.id.buy_power_bomb);
	        mBuyPowerBomb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
				}
	        });
	        
	        mBuyInvincibility = (Button)v.findViewById(R.id.buy_invincibility);
	        mBuyInvincibility.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
				}
	        });
	        
	        mBuyHealth = (Button)v.findViewById(R.id.buy_health);
	        mBuyHealth.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
				}
	        });
	        
	        mBuyStealth = (Button)v.findViewById(R.id.buy_stealth);
	        mBuyStealth.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
				}
	        });
	        
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
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode != Activity.RESULT_OK) return;
//		if (requestCode == REQUEST_DATE) {
//			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//			temp.setDate(date);
//			updateDate();
//		}
//	}
	
//	private void updateDate() {
//		mDateButton.setText(temp.getDate().toString());
//	}
}
