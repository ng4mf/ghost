package edu.cs2110.ghost;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActvitiy extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				try {
					sleep(500);
					startActivity(new Intent(getApplicationContext(), PostSplashActivity.class)); //Change "MainActivity" to whatever class you want to transition to
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};
		
		thread.start();
	}
	
}
