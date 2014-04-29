package edu.cs2110.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EndActivity extends Activity {

	private int score;
	private TextView finalScore;
	private LinearLayout layout;
	
	private Button mainMenu;
	private Button quit;
	
	private static final String TAG = "EndActivity";
	
	public EndActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		
		Intent i = getIntent();
		Bundle b = i.getExtras();
		
		Log.d(TAG, "Fine ");
		
		score = b.getInt("KillScore");
		
		Log.d(TAG, "Fine till here");
		
		finalScore = (TextView)findViewById(R.id.score);
		finalScore.setText("" + score);
		
		Log.d(TAG, "Created score fine");
		
		mainMenu = (Button)findViewById(R.id.goto_main);
		mainMenu.setText("Main Menu");
		mainMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(), PostSplashActivity.class));
			}
		});

		Log.d(TAG, "Made button 1");
		
		quit = (Button)findViewById(R.id.quit);
		quit.setText("Quit");
		quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			
		});
		
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		int oldScore = prefs.getInt("key", 0);  
		if(score > oldScore ){
		   Editor edit = prefs.edit();
		   edit.putInt("key", score);
		   edit.commit();
		}

		Log.d(TAG, "Made button 2");
	}

}
