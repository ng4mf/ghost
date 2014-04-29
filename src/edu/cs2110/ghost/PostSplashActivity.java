package edu.cs2110.ghost;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;

public class PostSplashActivity extends Activity {
	
	private int difficulty;
	static MenuFragment firstFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragmentContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            firstFragment = MenuFragment.newInstance();
            
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, firstFragment).commit();
        }
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty (int g) {
		difficulty = g;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//display Splash Screen and then transition

		//fm.beginTransaction().add(new MenuFragment()).commit();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//this.setContentView(R.layout.activity_fragment);
		//FragmentManager fm = getFragmentManager();
		//fm.beginTransaction().attach(new MenuFragment()).commit();
	}
	

}