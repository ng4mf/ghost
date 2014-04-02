package edu.cs2110.ghost;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;

public class StartActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//display Splash Screen and then transition

		this.setContentView(R.layout.fragment_menu);
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().attach(new MenuFragment()).commit();
	}
	
	@Override
	public void onResume() {
		this.setContentView(R.layout.fragment_menu);
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().attach(new MenuFragment()).commit();
	}
	

}